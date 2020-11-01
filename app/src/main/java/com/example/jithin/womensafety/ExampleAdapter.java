package com.example.jithin.womensafety;


import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Icon;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;



import java.util.List;

public class ExampleAdapter extends RecyclerView.Adapter<ExampleAdapter.ViewHolder> {
    Context context;
    List<ContactData> staff;

    public ExampleAdapter(Context friendsactivity, List<ContactData> dataModelList) {
        this.context = friendsactivity;
        this.staff = dataModelList;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //LayoutInflater inflater = LayoutInflater.from(context);
        //  View view = inflater.inflate(R.layout.list_staff, null);
        // return new ViewHolder(view);
        View rootView = LayoutInflater.from(context).inflate(R.layout.activity_exampleitem, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rootView.setLayoutParams(lp);
        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.name.setText(staff.get(position).getName());
        holder.no.setText(staff.get(position).getContact());
        holder.type.setText(staff.get(position).getType());

       // Picasso.with(this.context).load(staff.get(position).getImage()).into(holder.image);





        holder.lyt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContactData p = staff.get(position);
                String id = p.getId();
                String name = p.getName();
                String phone = p.getContact();
                String type = p.getType();

                Intent i=new Intent(context,Viewfriend.class);
                i.putExtra("type",type);
                i.putExtra("no",phone);
                i.putExtra("name",name);
                i.putExtra("id",id);
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return staff.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, type, no;
        ImageView image;
        LinearLayout lyt;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            type = itemView.findViewById(R.id.type);
            no = itemView.findViewById(R.id.no);
            lyt = itemView.findViewById(R.id.layout);

        }

    }

}