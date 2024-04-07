package com.example.asigment_and103.FragmentHome;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asigment_and103.Interfece.ItemClickListener;
import com.example.asigment_and103.Model.PRODUCT;
import com.example.asigment_and103.R;
import com.example.asigment_and103.ViewHolder.MenuViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class Home extends Fragment {
    FloatingActionButton flt_addSP;
    View view;
    RecyclerView rcvhome;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference product = firebaseDatabase.getReference("PRODUCT");
    RecyclerView.LayoutManager layoutManager;

    FirebaseRecyclerAdapter<PRODUCT, MenuViewHolder> adapter;
    FirebaseRecyclerOptions.Builder<PRODUCT> option = new FirebaseRecyclerOptions.Builder<PRODUCT>().setQuery(product,PRODUCT.class);

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
        rcvhome.setLayoutManager(layoutManager);
        layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        reloadFragment();
        return  view;
    }

    public void reloadFragment() {
         adapter = new FirebaseRecyclerAdapter<PRODUCT, MenuViewHolder>(option.build()) {
             @Override
             protected void onBindViewHolder(@NonNull MenuViewHolder menuViewHolder, int i, @NonNull PRODUCT product) {
                 menuViewHolder.txttensp.setText(product.getNamePRD());
                 menuViewHolder.txtgiasp.setText(product.getPricePRD());
                 Picasso.with(getContext()).load(product.getImagePRD()).into(menuViewHolder.img_home);
             }

             @NonNull
             @Override
             public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                 View view1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home,parent,true);

                 return new MenuViewHolder(view1);
             }
         };
         rcvhome.setAdapter(adapter);
    }
    private void addsp() {
        AlertDialog.Builder  builder= new AlertDialog.Builder(getContext());
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        view= layoutInflater.inflate(R.layout.dialog_product,null);
        EditText edt_tenSP = view.findViewById(R.id.edt_tenSP);
        EditText edt_giaSP = view.findViewById(R.id.edt_giaSP);
        Button btn_chooseImage = view.findViewById(R.id.btn_chooseImage);
        Button btn_upimage = view.findViewById(R.id.btn_upImage);


        btn_chooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btn_upimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
                       PRODUCT product1 = new PRODUCT(tensp,giasp,"");
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
    }
}