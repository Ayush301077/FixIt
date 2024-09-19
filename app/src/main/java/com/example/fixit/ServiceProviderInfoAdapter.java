package com.example.fixit;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ServiceProviderInfoAdapter extends RecyclerView.Adapter<ServiceProviderInfoAdapter.ViewHolder> {

    Context context;
    ArrayList<ServiceProviderInfo> serviceProviderInfoArrayList = new ArrayList<>();

    public ServiceProviderInfoAdapter(Context context, ArrayList<ServiceProviderInfo> serviceProviderInfoArrayList) {
        this.context = context;
        this.serviceProviderInfoArrayList = serviceProviderInfoArrayList;
    }

    public void setFilteredList(ArrayList<ServiceProviderInfo> filteredList){
        this.serviceProviderInfoArrayList = filteredList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ServiceProviderInfoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.service_provider_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceProviderInfoAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        ServiceProviderInfo info = serviceProviderInfoArrayList.get(position);

        holder.name.setText(serviceProviderInfoArrayList.get(position).name);
        holder.service.setText(serviceProviderInfoArrayList.get(position).services);
        holder.contact.setText(serviceProviderInfoArrayList.get(position).contact);
        holder.location.setText(serviceProviderInfoArrayList.get(position).city);
        Glide.with(context)
                .load(info.getProfileImage()) // URL of the image
                .placeholder(R.drawable.profile_icon) // Optional placeholder image
                .into(holder.profileImage);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.itemView.getContext(), ServiceProviderDetailsActtivity.class);
                intent.putExtra("name", serviceProviderInfoArrayList.get(position).name);
                intent.putExtra("service", serviceProviderInfoArrayList.get(position).services);
                intent.putExtra("contact", serviceProviderInfoArrayList.get(position).contact);
                intent.putExtra("location", serviceProviderInfoArrayList.get(position).city);
                intent.putExtra("name", serviceProviderInfoArrayList.get(position).name);
                intent.putExtra("profileImage", serviceProviderInfoArrayList.get(position).getProfileImage()); // pass the image URL
                intent.putExtra("email", serviceProviderInfoArrayList.get(position).email);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                holder.itemView.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return serviceProviderInfoArrayList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        TextView name, service,contact, location,ratings;
        ImageView profileImage;
        TextView email;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            service = itemView.findViewById(R.id.service);
            contact = itemView.findViewById(R.id.contact);
            location = itemView.findViewById(R.id.location);
            ratings = itemView.findViewById(R.id.ratings);
            profileImage = itemView.findViewById(R.id.profileImage);
            email = itemView.findViewById(R.id.email);

        }
    }
}



//import android.content.Context;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.os.AsyncTask;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import java.io.InputStream;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.util.ArrayList;
//
//public class ServiceProviderInfoAdapter extends RecyclerView.Adapter<ServiceProviderInfoAdapter.ViewHolder> {
//
//    private Context context;
//    private ArrayList<ServiceProviderInfo> serviceProviderInfoArrayList;
//
//    public ServiceProviderInfoAdapter(Context context, ArrayList<ServiceProviderInfo> serviceProviderInfoArrayList) {
//        this.context = context;
//        this.serviceProviderInfoArrayList = serviceProviderInfoArrayList;
//    }
//
//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        // Inflate your layout for the individual items in the RecyclerView
//        View view = LayoutInflater.from(context).inflate(R.layout.service_provider_item, parent, false);
//        return new ViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        // Get the current service provider information
//        ServiceProviderInfo serviceProviderInfo = serviceProviderInfoArrayList.get(position);
//
//        // Set text views
//        holder.name.setText(serviceProviderInfo.getName());
//        holder.service.setText(serviceProviderInfo.getService());
//
//        // Get the profile image URL from the ServiceProviderInfo object
//        String imageUrl = serviceProviderInfo.getProfilephoto();
//
//        // Check if imageUrl is not null or empty
//        if (imageUrl != null && !imageUrl.isEmpty()) {
//            // Use the AsyncTask to download and set the image in the ImageView
//            new ImageLoaderTask(holder.profile_image).execute(imageUrl);
//        } else {
//            // Set a default image if no profile image URL is available
//            holder.profile_image.setImageResource(R.drawable.profile_icon);
//        }
//    }
//
//    @Override
//    public int getItemCount() {
//        return serviceProviderInfoArrayList.size();
//    }
//
//    // ViewHolder class to hold the views in each item
//    public static class ViewHolder extends RecyclerView.ViewHolder {
//
//        TextView name, service;
//        ImageView profile_image;
//
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//
//            name = itemView.findViewById(R.id.name);
//            service = itemView.findViewById(R.id.service);
//            profile_image = itemView.findViewById(R.id.profile_image);
//        }
//    }
//
//    // AsyncTask to load images in the background
//    private class ImageLoaderTask extends AsyncTask<String, Void, Bitmap> {
//
//        private ImageView imageView;
//
//        public ImageLoaderTask(ImageView imageView) {
//            this.imageView = imageView;
//        }
//
//        @Override
//        protected Bitmap doInBackground(String... urls) {
//            String url = urls[0];
//            Bitmap image = null;
//            try {
//                // Open a connection to the URL
//                URL imageUrl = new URL(url);
//                HttpURLConnection connection = (HttpURLConnection) imageUrl.openConnection();
//                connection.setDoInput(true);
//                connection.connect();
//
//                // Read the input stream and decode it into a bitmap
//                InputStream input = connection.getInputStream();
//                image = BitmapFactory.decodeStream(input);
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            return image;
//        }
//
//        @Override
//        protected void onPostExecute(Bitmap bitmap) {
//            if (bitmap != null) {
//                // Set the loaded bitmap to the ImageView
//                imageView.setImageBitmap(bitmap);
//            } else {
//                // If something went wrong, you can set a default or error image
//                imageView.setImageResource(R.drawable.image1);
//            }
//        }
//    }
//}
