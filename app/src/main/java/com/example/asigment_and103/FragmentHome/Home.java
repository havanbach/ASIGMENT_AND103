package com.example.asigment_and103.FragmentHome;

import static com.example.asigment_and103.Comon.Comon.PICK_IMAGE_REQUEST;
import static com.example.asigment_and103.Comon.Comon.RESULT_OK;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asigment_and103.Comon.Comon;
import com.example.asigment_and103.Interfece.ItemClickListener;
import com.example.asigment_and103.Model.PRODUCT;
import com.example.asigment_and103.R;
import com.example.asigment_and103.ViewHolder.MenuViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.UUID;

public class Home extends Fragment {
    PRODUCT product1;
    FloatingActionButton flt_addSP;
    EditText edt_tenSP,edt_giaSP;
    View view;
    RecyclerView rcvhome;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference product = firebaseDatabase.getReference("PRODUCT");
    RecyclerView.LayoutManager layoutManager;

    FirebaseRecyclerAdapter<PRODUCT, MenuViewHolder> adapter;
    private StorageReference storageReference;
    private Uri saveUri;

    public Home() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        flt_addSP = view.findViewById(R.id.flt_addSP);
        rcvhome = view.findViewById(R.id.rcvHome);
        flt_addSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addsp();
            }
        });
        rcvhome.setHasFixedSize(false);
        layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        rcvhome.setLayoutManager(layoutManager);
        reloadFragment();
        return  view;
    }

    public void reloadFragment() {
        adapter=new FirebaseRecyclerAdapter<PRODUCT, MenuViewHolder>(PRODUCT.class,R.layout.item_home,MenuViewHolder.class,product) {
            @Override
            protected void populateViewHolder(MenuViewHolder menuViewHolder, PRODUCT product, int i) {
                menuViewHolder.txttensp.setText(product.getNamePRD());
                menuViewHolder.txtgiasp.setText(product.getPricePRD());
                Picasso.with(getContext()).load(product.getImagePRD()).into(menuViewHolder.img_home);
            }
        };
         rcvhome.setAdapter(adapter);
    }
    private void addsp() {
        AlertDialog.Builder  builder= new AlertDialog.Builder(getContext());
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        view= layoutInflater.inflate(R.layout.dialog_product,null);
        edt_tenSP = view.findViewById(R.id.edt_tenSP);
        edt_giaSP = view.findViewById(R.id.edt_giaSP);
        Button btn_chooseImage = view.findViewById(R.id.btn_chooseImage);
        Button btn_upimage = view.findViewById(R.id.btn_upImage);


        btn_chooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });
        btn_upimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeImage();
            }
        });
        builder.setPositiveButton("Thêm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               product.addValueEventListener(new ValueEventListener() {
                   @Override
                   public void onDataChange(@NonNull DataSnapshot snapshot) {
                       final String tensp = edt_tenSP.getText().toString();
                       final String giasp = edt_giaSP.getText().toString();

                       if (tensp.isEmpty() || giasp.isEmpty()){
                           Toast.makeText(getContext(), "Vui lòng điền đẩy đủ thông tin", Toast.LENGTH_SHORT).show();
                           return;
                       }
                       product.child(tensp).setValue(product1);
                       Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();

                   }

                   @Override
                   public void onCancelled(@NonNull DatabaseError error) {
                       Toast.makeText(getContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                   }
               });
            }
        });
        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                return;
            }
        });
        builder.setView(view);
        builder.show();
    };
    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Chọn Ảnh"),PICK_IMAGE_REQUEST);
    };
    private void changeImage() {
        if (saveUri != null) {
            ProgressDialog dialog = new ProgressDialog(getContext());
            dialog.setMessage("Đang Tải Lên ...");
            dialog.show();

            String imageName = UUID.randomUUID().toString();
            StorageReference imageFoder = storageReference.child("image/" + imageName);
            imageFoder.putFile(saveUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    dialog.dismiss();
                    Toast.makeText(getContext(), "Đã Tải Lên !!", Toast.LENGTH_SHORT).show();
                    imageFoder.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            product1 = new PRODUCT(edt_tenSP.getText().toString(),edt_giaSP.getText().toString(),uri.toString());
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    dialog.dismiss();
                    Toast.makeText(getContext(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                    double progress = (100.0 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                    dialog.setMessage("Tải Lên" + progress + "%");
                }
            });

        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            saveUri = data.getData();
            Toast.makeText(getContext(), "Đã chọn", Toast.LENGTH_SHORT).show();
        }

    }
}