package com.example.midtermsexam_beauty.views.seller;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.midtermsexam_beauty.R;
import com.example.midtermsexam_beauty.adapters.SellerNavCard;
import com.example.midtermsexam_beauty.models.MenuItem;
import com.example.midtermsexam_beauty.utilities.SupabaseAuthService;
import com.example.midtermsexam_beauty.utilities.SessionManager;
import com.example.midtermsexam_beauty.adapters.MenuItemAdapter;

import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;

import java.io.InputStream;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SellerMenu extends AppCompatActivity {

    private RecyclerView rvMenu;
    private ProgressBar loader;
    private LinearLayout emptyState;
    private MenuItemAdapter adapter;

    private final SupabaseAuthService supabase = new SupabaseAuthService();
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final Handler handler = new Handler(Looper.getMainLooper());

    private String token, sellerId;

    // For image picking in dialog
    private Uri selectedImageUri = null;
    private ImageView dialogImagePreview = null;

    private final ActivityResultLauncher<Intent> imagePickerLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    selectedImageUri = result.getData().getData();
                    if (dialogImagePreview != null && selectedImageUri != null) {
                        Glide.with(this).load(selectedImageUri).into(dialogImagePreview);
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_menu);
        SellerNavCard.setupNavbar(this);

        rvMenu = findViewById(R.id.rvMenu);
        loader = findViewById(R.id.loader);
        emptyState = findViewById(R.id.emptyState);
        Button btnAddItem = findViewById(R.id.btnAddItem);

        SessionManager session = new SessionManager(this);
        token = session.getToken();
        sellerId = session.getSellerId();

        rvMenu.setLayoutManager(new LinearLayoutManager(this));

        btnAddItem.setOnClickListener(v -> showMenuItemDialog(null));

        loadMenuItems();
    }

    private void loadMenuItems() {
        loader.setVisibility(View.VISIBLE);
        rvMenu.setVisibility(View.GONE);
        emptyState.setVisibility(View.GONE);

        executor.execute(() -> {
            List<MenuItem> items = supabase.getMenuItems(token, sellerId);
            handler.post(() -> {
                loader.setVisibility(View.GONE);
                if (items.isEmpty()) {
                    emptyState.setVisibility(View.VISIBLE);
                } else {
                    rvMenu.setVisibility(View.VISIBLE);
                    adapter = new MenuItemAdapter(items,
                            item -> showMenuItemDialog(item),   // edit
                            item -> confirmDelete(item),         // delete
                            item -> toggleAvailability(item)     // toggle
                    );
                    rvMenu.setAdapter(adapter);
                }
            });
        });
    }

    private void toggleAvailability(MenuItem item) {
        executor.execute(() -> {
            boolean success = supabase.updateMenuItem(token, item);
            handler.post(() -> {
                if (!success) {
                    Toast.makeText(this, "Failed to update availability.", Toast.LENGTH_SHORT).show();
                    // Revert the toggle if it failed
                    item.setAvailable(!item.isAvailable());
                    adapter.notifyDataSetChanged();
                }
            });
        });
    }

    private void showMenuItemDialog(MenuItem existing) {
        boolean isEdit = existing != null;
        selectedImageUri = null;

        View view = LayoutInflater.from(this).inflate(R.layout.dialog_menu_item, null);

        EditText etName        = view.findViewById(R.id.etName);
        EditText etDescription = view.findViewById(R.id.etDescription);
        EditText etPrice       = view.findViewById(R.id.etPrice);
        EditText etCategory    = view.findViewById(R.id.etCategory);
        ImageView ivPreview    = view.findViewById(R.id.ivImagePreview);
        Button btnPickImage    = view.findViewById(R.id.btnPickImage);

        dialogImagePreview = ivPreview;

        if (isEdit) {
            etName.setText(existing.getName());
            etDescription.setText(existing.getDescription());
            etPrice.setText(String.valueOf(existing.getPrice()));
            etCategory.setText(existing.getCategory());
            if (existing.getImageUrl() != null && !existing.getImageUrl().isEmpty()) {
                Glide.with(this).load(existing.getImageUrl()).into(ivPreview);
            }
        }

        btnPickImage.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            imagePickerLauncher.launch(intent);
        });

        new AlertDialog.Builder(this)
                .setTitle(isEdit ? "Edit Item" : "Add Item")
                .setView(view)
                .setPositiveButton("Save", (dialog, which) -> {
                    String name  = etName.getText().toString().trim();
                    String desc  = etDescription.getText().toString().trim();
                    String priceStr = etPrice.getText().toString().trim();
                    String cat   = etCategory.getText().toString().trim();

                    if (name.isEmpty() || priceStr.isEmpty()) {
                        Toast.makeText(this, "Name and price are required.", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    double price;
                    try { price = Double.parseDouble(priceStr); }
                    catch (NumberFormatException e) {
                        Toast.makeText(this, "Invalid price.", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    MenuItem item = isEdit ? existing : new MenuItem();
                    item.setSellerId(sellerId);
                    item.setName(name);
                    item.setDescription(desc);
                    item.setPrice(price);
                    item.setCategory(cat);
                    item.setAvailable(true);

                    saveMenuItem(item, isEdit);
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void saveMenuItem(MenuItem item, boolean isEdit) {
        loader.setVisibility(View.VISIBLE);

        executor.execute(() -> {
            // Upload image first if one was selected
            if (selectedImageUri != null) {
                String imageUrl = uploadImage(selectedImageUri, sellerId);
                if (imageUrl != null) item.setImageUrl(imageUrl);
            }

            boolean success;
            if (isEdit) {
                success = supabase.updateMenuItem(token, item);
            } else {
                success = supabase.addMenuItem(token, item);
            }

            handler.post(() -> {
                loader.setVisibility(View.GONE);
                if (success) {
                    Toast.makeText(this, isEdit ? "Item updated!" : "Item added!", Toast.LENGTH_SHORT).show();
                    loadMenuItems();
                } else {
                    Toast.makeText(this, "Failed to save item.", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    private String uploadImage(Uri uri, String sellerId) {
        try {
            InputStream is = getContentResolver().openInputStream(uri);
            if (is == null) return null;
            byte[] data = is.readAllBytes();
            is.close();

            String mimeType = getContentResolver().getType(uri);
            if (mimeType == null) mimeType = "image/jpeg";

            String ext = mimeType.contains("png") ? "png" : "jpg";
            String path = sellerId + "/" + UUID.randomUUID() + "." + ext;

            return supabase.uploadImage(token, "menu-items", path, data, mimeType);
        } catch (Exception e) {
            return null;
        }
    }

    private void confirmDelete(MenuItem item) {
        new AlertDialog.Builder(this)
                .setTitle("Delete Item")
                .setMessage("Are you sure you want to delete \"" + item.getName() + "\"?")
                .setPositiveButton("Delete", (d, w) -> {
                    loader.setVisibility(View.VISIBLE);
                    executor.execute(() -> {
                        boolean success = supabase.deleteMenuItem(token, item.getId());
                        handler.post(() -> {
                            loader.setVisibility(View.GONE);
                            Toast.makeText(this, success ? "Deleted." : "Failed to delete.", Toast.LENGTH_SHORT).show();
                            if (success) loadMenuItems();
                        });
                    });
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}