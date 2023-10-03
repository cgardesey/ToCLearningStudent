package com.prepeez.toclearningstudent.adapter;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.prepeez.toclearningstudent.activity.ChatActivity;
import com.prepeez.toclearningstudent.activity.PictureActivity;
import com.prepeez.toclearningstudent.R;
import com.prepeez.toclearningstudent.pojo.DateItem;
import com.prepeez.toclearningstudent.pojo.Message;
import com.shockwave.pdfium.PdfiumCore;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.prepeez.toclearningstudent.activity.ChatActivity.TYPE_DATE;
import static com.prepeez.toclearningstudent.activity.ChatActivity.TYPE_MESSAGE;
import static com.prepeez.toclearningstudent.activity.ChatActivity.context;
import static com.prepeez.toclearningstudent.activity.ChatActivity.mChatDocsStorageReference;
import static com.prepeez.toclearningstudent.activity.ChatActivity.mChatPhotosStorageReference;
import static com.prepeez.toclearningstudent.activity.ChatActivity.mClassDatabaseReference;
import static com.prepeez.toclearningstudent.activity.ChatActivity.mFirebaseAuth;
import static com.prepeez.toclearningstudent.activity.ChatActivity.mUserId;
import static com.prepeez.toclearningstudent.activity.ChatActivity.sfd_file_name;
import static com.prepeez.toclearningstudent.activity.ChatActivity.sfd_time;
import static com.prepeez.toclearningstudent.util.PixelUtil.dpToPx;


/**
 * Created by 2CLearning on 2/8/2018.
 */

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = "ChatAdapter";
    public ArrayList<Object> selected_usersList = new ArrayList<>();
    public ArrayList<Object> consolidatedList;
    String myUserName;
    Activity activity;
    DatabaseReference databaseReference;
    public static Bitmap bitmap;
    boolean photoAlreadyLoaded, docAlreadyLoaded, mapAlreadyLoaded;
    String timeStamp = null;


    String destinationPath;

    public ChatAdapter(ArrayList<Object> consolidatedList, String myUserName, Activity activity, DatabaseReference databaseReference) {
        this.consolidatedList = consolidatedList;
        this.myUserName = myUserName;
        this.activity = activity;
        this.databaseReference = databaseReference;
    }

    @Override
    public int getItemViewType(int position) {
        int viewType = 0;
        if (consolidatedList.get(position) instanceof DateItem) {
            viewType = TYPE_DATE;
        } else if (consolidatedList.get(position) instanceof Message) {
            viewType = TYPE_MESSAGE;
        }
        return viewType;
    }

    @Override
    public int getItemCount() {
        return consolidatedList != null ? consolidatedList.size() : 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {

            case TYPE_MESSAGE:
                View v1 = inflater.inflate(R.layout.list_item_chat, parent, false);
                viewHolder = new MessageViewHolder(v1, activity);
                break;

            case TYPE_DATE:
                View v2 = inflater.inflate(R.layout.list_item_date, parent, false);
                viewHolder = new DateViewHolder(v2);
                break;
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {

        switch (viewHolder.getItemViewType()) {

            case TYPE_MESSAGE:
                final Message message = (Message) consolidatedList.get(position);
                final MessageViewHolder holder = (MessageViewHolder) viewHolder;
                final java.util.Date time = new java.util.Date(message.getTimestampCreatedLong());
                timeStamp = sfd_file_name.format(time);

                final boolean messageIsMine = message.getUserId().equals(mUserId);

                boolean isPhoto = message.getPhotoUrl() != null;
                boolean isDoc = message.getDocUrl() != null;
                boolean isMsg = message.getText() != null;
                final boolean isMap = message.getMapUrl() != null;

                final File extStorageDir = Environment.getExternalStorageDirectory();

                String mapUrl = message.getMapUrl() == null ? "" : message.getMapUrl();
                String photoUrl = message.getPhotoUrl() == null ? "" : message.getPhotoUrl();
                final String docUrl = message.getDocUrl() == null ? "" : message.getDocUrl();

                holder.name.setText(message.getName());
                holder.time.setText(sfd_time.format(time));

                if (messageIsMine) {
                    holder.layout.setBackgroundResource(R.drawable.bubble_in);
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) holder.layout.getLayoutParams();
                    params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                    params.setMargins(dpToPx(activity,64), dpToPx(activity,0), dpToPx(activity,0), dpToPx(activity,0));
                    holder.layout.setLayoutParams(params);
                } else {
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) holder.layout.getLayoutParams();
                    params.setMargins(dpToPx(activity,0), dpToPx(activity,0), dpToPx(activity,64), dpToPx(activity,0));
                    holder.layout.setLayoutParams(params);
                }

                imgLoadedStatus(message);
                docLoadedStatus(message);
                mapLoadedStatus(message);


                if (messageIsMine) {
                    holder.statusImg.setVisibility(View.VISIBLE);
                    Bitmap bitmap;
                    if (isMsg) {
                        bitmap = BitmapFactory.decodeResource(activity.getResources(), R.drawable.ic_action_tick);
                    } else {
                        String compositeUrl = mapUrl + photoUrl + docUrl;
                        if ((compositeUrl).substring(0, 3).equals("URI")) {
                            bitmap = BitmapFactory.decodeResource(activity.getResources(), R.mipmap.ic_timer_round);

                        } else {
                            bitmap = BitmapFactory.decodeResource(activity.getResources(), R.drawable.ic_action_tick);
                        }
                    }
                    holder.statusImg.setImageBitmap(bitmap);
                }
                else {
                    holder.statusImg.setVisibility(View.GONE);
                }



                if (isMap) {
                    holder.metaData.setVisibility(View.GONE);
                    holder.docFrame.setVisibility(View.GONE);
                    holder.picFrame.setVisibility(View.VISIBLE);
                    holder.txtMsgFrame.setVisibility(View.GONE);
                    StorageReference storageRef_map = mChatPhotosStorageReference;
                    if (messageIsMine) {

                        File file = new File(extStorageDir + "/2l/Maps/Sent", "IMG-" + timeStamp + ".jpeg");
                        if (file.exists()) {
                            String imgLoc = extStorageDir.getAbsolutePath() + "/2l/Maps/Sent/" + "IMG-" + timeStamp + ".jpeg";
                            Bitmap bitmap = BitmapFactory.decodeFile(imgLoc);

                            holder.image.setImageBitmap(bitmap);
                        }
                    } else {
                        if (!mapAlreadyLoaded) {
                            Glide.with(holder.image.getContext())
                                    .load(message.getMapUrl())
                                    .asBitmap()
                                    .listener(new RequestListener<String, Bitmap>() {
                                        @Override
                                        public boolean onException(Exception e, String model, Target<Bitmap> target, boolean isFirstResource) {
                                            return false;
                                        }

                                        @Override
                                        public boolean onResourceReady(Bitmap resource, String model, Target<Bitmap> target, boolean isFromMemoryCache, boolean isFirstResource) {
                                            holder.pbar_pic.setVisibility(View.GONE);

                                            File file = new File(extStorageDir + "/2l/Maps", "Received");
                                            if (!file.exists()) {
                                                file.mkdirs();
                                            }
                                            String destinationPath = extStorageDir.getAbsolutePath() + "/2l/Maps/Received/" + "IMG-" + timeStamp + ".jpeg";

                                            // Next, create your specific file for image storage:

                                            File image = new File(destinationPath);
                                            // After that, you just have to write the Bitmap thanks to its method compress such as:

                                            boolean success = false;

                                            // Encode the file as a JPEG image.
                                            FileOutputStream outStream;
                                            try {
                                                outStream = new FileOutputStream(image);
                                                /* 100 to keep full quality of the image */
                                                resource.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
                                                outStream.flush();
                                                outStream.close();
                                                success = true;
                                            } catch (FileNotFoundException e) {
                                                e.printStackTrace();
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }

                                            if (success) {
                                                mClassDatabaseReference.child("imgLoaded").child(String.valueOf(message.getTimestampCreatedLong())).child(message.getUserId()).setValue("true");
                                                Log.d(TAG, "Error during image saving");
                                            } else {
                                                Log.d(TAG, "Error during image saving");
                                            }
                                            return false;
                                        }
                                    })
                                    .into(holder.image);
                        } else {

                            File file = new File(extStorageDir + "/2l/Maps/Received", "IMG-" + timeStamp + ".jpeg");
                            if (file.exists()) {
                                String imgLoc = extStorageDir.getAbsolutePath() + "/2l/Maps/Received/" + "IMG-" + timeStamp + ".jpeg";
                                Bitmap bitmap2 = BitmapFactory.decodeFile(imgLoc);
                                holder.image.setImageBitmap(bitmap2);

                            }
                            holder.pbar_pic.setVisibility(View.GONE);


                        }

                    }
                }

                if (isPhoto) {
                    holder.metaData.setVisibility(View.GONE);
                    holder.docFrame.setVisibility(View.GONE);
                    holder.picFrame.setVisibility(View.VISIBLE);
                    holder.txtMsgFrame.setVisibility(View.GONE);
                    StorageReference storageRef_pic = mChatPhotosStorageReference.child(message.getKey());

                    if (messageIsMine) {
                        if (photoUrl.substring(0, 3).equals("URI")) {
                            holder.downloadStatusWrapper_pic.setVisibility(View.VISIBLE);
                            holder.retry.setVisibility(View.GONE);
                            final StorageTask<UploadTask.TaskSnapshot> uploadTask_pic;
                            File file = new File(photoUrl.substring(3));
                            final Uri uri = Uri.fromFile(file);
                            uploadTask_pic = storageRef_pic.child(message.getKey()).putFile(uri)
                                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                            final Uri downloadUri = taskSnapshot.getDownloadUrl();

                                            Map msgInfo = new HashMap();
                                            msgInfo.put("photoUrl", downloadUri.toString());
                                            mClassDatabaseReference.child("objects").child(message.getKey()).updateChildren(msgInfo);

                                            int size = ChatActivity.objects.size();
                                            for (int i = size; i > 0; i--) {

                                                Object existingObj = ChatActivity.objects.get(i - 1);

                                                if (existingObj instanceof Message) {

                                                    Message existingMsg = (Message) existingObj;

                                                    if (message.getKey().equals(existingMsg.getKey())) {
                                                        existingMsg.setPhotoUrl(downloadUri.toString());
                                                        ChatActivity.objects.set(i - 1, existingMsg);
                                                        notifyDataSetChanged();
                                                    }
                                                }
                                            }
                                        }
                                    })
                                    .addOnProgressListener(new com.google.firebase.storage.OnProgressListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                            float progress = (100.0F * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                                            holder.pbar_pic.setProgress((int) progress);
                                            holder.downloadStatusWrapper_pic.setVisibility(View.VISIBLE);
                                            holder.retry.setVisibility(View.GONE);
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            holder.downloadStatusWrapper_pic.setVisibility(View.GONE);
                                            holder.retry.setVisibility(View.VISIBLE);
                                            holder.retry_text.setText(context.getResources().getString(R.string.retry));
                                        }
                                    })
                                    .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                            holder.downloadStatusWrapper_pic.setVisibility(View.GONE);
                                            holder.retry.setVisibility(View.GONE);
                                        }
                                    });

                            final StorageReference finalStorageRef = storageRef_pic;
                            holder.downloadStatusWrapper_pic.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    if (!uploadTask_pic.isCanceled()){
                                        boolean cancel = uploadTask_pic.cancel();
                                        Log.d("wotiriso", Boolean.toString(cancel));
                                    }
                                    else {
                                        finalStorageRef.child(message.getKey()).delete();
                                    }

                                }
                            });
                            holder.retry.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

//                                    uploadTask = null;
//                                    Log.d("wotiriso", Boolean.toString(cancel));
                                }
                            });
                        }

                        File file = new File(extStorageDir + "/2l/Pictures/Sent", "IMG-" + timeStamp + ".jpeg");
                        if (file.exists()) {
                            String imgLoc = extStorageDir.getAbsolutePath() + "/2l/Pictures/Sent/" + "IMG-" + timeStamp + ".jpeg";
                            Bitmap bitmap = BitmapFactory.decodeFile(imgLoc);

                            holder.image.setImageBitmap(bitmap);
                        }

                    } else {
                        if (!photoAlreadyLoaded) {
                            final StorageTask<FileDownloadTask.TaskSnapshot> downloadTask;
                            holder.downloadStatusWrapper_pic.setVisibility(View.VISIBLE);
                            File file = new File(extStorageDir + "/2l/Pictures", "Received");
                            if (!file.exists()) {
                                file.mkdirs();
                            }

                            final String destinationPath = extStorageDir.getAbsolutePath() + "/2l/Pictures/Received/" + "IMG-" + timeStamp + ".jpeg";

                            File localFile = new File(destinationPath);

                            downloadTask = storageRef_pic.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                    // Local temp file has been created
                                    holder.downloadStatusWrapper_pic.setVisibility(View.GONE);
                                    holder.retry.setVisibility(View.GONE);
                                    Drawable drawable = Drawable.createFromPath(destinationPath);
                                    holder.image.setImageDrawable(drawable);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                    holder.downloadStatusWrapper_pic.setVisibility(View.GONE);
                                    holder.retry.setVisibility(View.VISIBLE);
                                    holder.retry_text.setText(message.getMetaData());
                                }
                            }).addOnProgressListener(new com.google.firebase.storage.OnProgressListener<FileDownloadTask.TaskSnapshot>() {
                                @Override
                                public void onProgress(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                    float progress = (100.0F * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                                    holder.pbar_pic.setProgress((int) progress);
                                    holder.downloadStatusWrapper_pic.setVisibility(View.VISIBLE);
                                    holder.retry.setVisibility(View.GONE);
                                }
                            });
                            holder.downloadStatusWrapper_doc.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    downloadTask.cancel();
                                }
                            });

                        } else {
                            File file = new File(extStorageDir + "/2l/Pictures/Received", "IMG-" + timeStamp + ".jpeg");
                            if (file.exists()) {
                                String imgLoc = extStorageDir.getAbsolutePath() + "/2l/Pictures/Received/" + "IMG-" + timeStamp + ".jpeg";
                                Bitmap bitmap2 = BitmapFactory.decodeFile(imgLoc);
                                holder.image.setImageBitmap(bitmap2);
                            }
                        }

                    }

                }

                if (isDoc) {
                    holder.pdfImg.setVisibility(View.GONE);
                    holder.metaData.setVisibility(View.VISIBLE);
                    holder.docFrame.setVisibility(View.VISIBLE);
                    holder.picFrame.setVisibility(View.GONE);
                    holder.txtMsgFrame.setVisibility(View.GONE);
                    String ext = message.getExt();
                    String docType = getDocType(MimeTypeMap.getSingleton().getMimeTypeFromExtension(ext.substring(1)));
                    Bitmap docIconBitmap = null;
                    File docFile = null;
                    StorageReference storageRef_doc = mChatDocsStorageReference.child(message.getKey());


                    if (docType.equals("word")) {

                        docIconBitmap = BitmapFactory.decodeResource(activity.getResources(), R.mipmap.ic_doc);
                    } else if (docType.equals("excel")) {

                        docIconBitmap = BitmapFactory.decodeResource(activity.getResources(), R.mipmap.ic_xls);
                    } else if (docType.equals("powerpoint")) {

                        docIconBitmap = BitmapFactory.decodeResource(activity.getResources(), R.mipmap.ic_ppt);
                    } else if (docType.equals("pdf")) {

                        docIconBitmap = BitmapFactory.decodeResource(activity.getResources(), R.mipmap.ic_pdf);
                    } else if (docType.equals("text")) {

                        docIconBitmap = BitmapFactory.decodeResource(activity.getResources(), R.mipmap.ic_txt);
                    }

                    holder.docIcon.setImageBitmap(docIconBitmap);
                    holder.docTitle.setText(message.getDocTitle());
                    holder.metaData.setText(message.getMetaData());

                    if (messageIsMine) {
                        final StorageTask<UploadTask.TaskSnapshot> uploadTask_doc;
                        docFile = new File(extStorageDir + "/2l/Documents/Sent", "DOC-" + timeStamp + ext);

                        if (docUrl.substring(0, 3).equals("URI")) {
                            holder.downloadStatusWrapper_doc.setVisibility(View.VISIBLE);
                            File file = new File(docUrl.substring(3));
                            final Uri uri = Uri.fromFile(file);
                            uploadTask_doc = storageRef_doc.child(message.getKey()).putFile(uri)
                                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                            final Uri downloadUri = taskSnapshot.getDownloadUrl();

                                            Map msgInfo = new HashMap();
                                            msgInfo.put("docUrl", downloadUri.toString());
                                            mClassDatabaseReference.child("objects").child(message.getKey()).updateChildren(msgInfo);

                                            int size = ChatActivity.objects.size();
                                            for (int i = size; i > 0; i--) {

                                                Object existingObj = ChatActivity.objects.get(i - 1);

                                                if (existingObj instanceof Message) {

                                                    Message existingMsg = (Message) existingObj;

                                                    if (message.getKey().equals(existingMsg.getKey())) {
                                                        existingMsg.setDocUrl(downloadUri.toString());
                                                        ChatActivity.objects.set(i - 1, existingMsg);
                                                        notifyDataSetChanged();
                                                    }
                                                }
                                            }
                                        }
                                    })
                                    .addOnProgressListener(new com.google.firebase.storage.OnProgressListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                            float progress = (100.0F * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                                            holder.pbar_doc.setProgress((int) progress);
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            holder.uploadImg_pic.setImageBitmap(BitmapFactory.decodeResource(activity.getResources(), R.drawable.upload));
                                            holder.pbar_doc.setProgress((int) 0.0F);
                                            Log.d("wotiriso Error", e.toString());
                                        }
                                    })
                                    .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                            holder.downloadStatusWrapper_doc.setVisibility(View.GONE);
                                        }
                                    });

                            final StorageReference finalStorageRef_doc = storageRef_doc;
                            holder.downloadStatusWrapper_doc.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    if (!uploadTask_doc.isCanceled()){
                                        boolean cancel = uploadTask_doc.cancel();
                                        Log.d("wotiriso", Boolean.toString(cancel));
                                    }
                                    else {
                                        finalStorageRef_doc.child(message.getKey()).delete();
                                    }
                                }
                            });
                        }

                    } else {
                        if (!docAlreadyLoaded) {
                            final StorageTask<FileDownloadTask.TaskSnapshot> downloadTask;
                            holder.downloadStatusWrapper_pic.setVisibility(View.VISIBLE);
                            holder.uploadImg_pic.setImageBitmap(BitmapFactory.decodeResource(activity.getResources(), R.drawable.cancel));
                            File file = new File(extStorageDir + "/2l/Documents", "Received");
                            if (!file.exists()) {
                                file.mkdirs();
                            }

                            destinationPath = extStorageDir.getAbsolutePath() + "/2l/Documents/Received/";
                            docFile = new File(extStorageDir + "/2l/Documents/Received", "DOC-" + timeStamp + ext);
                            File localFile = new File(destinationPath);
                            downloadTask = storageRef_doc.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                    // Local temp file has been created
                                    holder.downloadStatusWrapper_doc.setVisibility(View.GONE);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                    holder.uploadImg_pic.setImageBitmap(BitmapFactory.decodeResource(activity.getResources(), R.drawable.download));
                                    holder.pbar_doc.setProgress((int) 0.0F);
                                }
                            }).addOnProgressListener(new com.google.firebase.storage.OnProgressListener<FileDownloadTask.TaskSnapshot>() {
                                @Override
                                public void onProgress(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                    float progress = (100.0F * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                                    holder.pbar_doc.setProgress((int) progress);
                                }
                            });
                            holder.downloadStatusWrapper_doc.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    downloadTask.cancel();
                                }
                            });
                        }

                    }

                    if (docFile.exists() && docType.equals("pdf")) {
                        File pdfImgFile = new File(extStorageDir + "/2l/PDF", "PDF-" + timeStamp + ".png");

                        if (!pdfImgFile.exists()) {
                            generateImageFromPdf(Uri.fromFile(docFile));
                        }
                        String pdfPgPrevLoc = extStorageDir.getAbsolutePath() + "/2l/PDF/" + "PDF-" + timeStamp + ".png";
                        Bitmap toBeCropped = BitmapFactory.decodeFile(pdfPgPrevLoc);
                        int fromHere = (int) (toBeCropped.getHeight() * 0.5);
                        Bitmap croppedBitmap = Bitmap.createBitmap(toBeCropped, 0, 0, toBeCropped.getWidth(), fromHere);
                        int pageCount = getPageCount(Uri.fromFile(docFile));
                        String pg = pageCount == 1 ? "1 page ∙ " : pageCount + " pages ∙ ";
                        holder.metaData.setText(pg + message.getMetaData());
                        //holder.metaData.setGravity(Gravity.CENTER);
                        holder.pdfImg.setVisibility(View.VISIBLE);
                        holder.pdfImg.setImageBitmap(croppedBitmap);
                    }
                }

                if (isMsg) {
                    holder.metaData.setVisibility(View.GONE);
                    holder.docFrame.setVisibility(View.GONE);
                    holder.picFrame.setVisibility(View.GONE);
                    holder.txtMsgFrame.setVisibility(View.VISIBLE);

                    holder.txtMsg.setText(message.getText());
                    boolean linkPrevExists = message.getTittle() != null || message.getDesc() != null;
                    if (linkPrevExists) {
                        holder.linkPrevFrame.setVisibility(View.VISIBLE);
                        holder.close.setVisibility(View.GONE);
                        holder.linkImg.setVisibility(View.GONE);

                        holder.linkTitle.setText(message.getTittle());
                        holder.linkDesc.setText(message.getDesc());
                    }
                }

                holder.txtMsg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isLink = message.getLink() != null && !message.getLink().equals("");
                        if (isLink) {
                            Uri webpage = Uri.parse(message.getLink());
                            Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                            if (intent.resolveActivity(activity.getPackageManager()) != null) {
                                activity.startActivity(intent);
                            }
                        }
                    }
                });

                holder.linkPrevFrame.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!message.getLink().equals("")) {
                            Uri webpage = Uri.parse(message.getLink());
                            Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                            if (intent.resolveActivity(activity.getPackageManager()) != null) {
                                activity.startActivity(intent);
                            }
                        }
                    }
                });

                holder.image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        boolean imgViewOnlyVisible = true;
                        if (holder.downloadStatusWrapper_pic.getVisibility() == View.VISIBLE) {
                            imgViewOnlyVisible = false;
                        }
                        else if (holder.retry.getVisibility() == View.VISIBLE) {
                            imgViewOnlyVisible = false;
                        }
                        if (imgViewOnlyVisible) {
                            if (holder.image.getDrawable() == null) {
                                Toast.makeText(activity, "Sorry, this media file doesn't exist on your internal storage", Toast.LENGTH_SHORT).show();
                            } else {

                                if (isMap) {
                                    Uri gmmIntentUri = Uri.parse("geo:" + message.getLatlng());
                                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                                    mapIntent.setPackage("com.google.android.apps.maps");
                                    activity.startActivity(mapIntent);
                                } else {
                                    bitmap = ((BitmapDrawable) holder.image.getDrawable()).getBitmap();
                                    Intent intent = new Intent(activity, PictureActivity.class);
                                    intent.putExtra("url", message.getPhotoUrl());
                                    activity.startActivity(intent);
                                }
                            }
                        }
                    }
                });

                holder.docFrame.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        String parent;
                        if (messageIsMine) {
                            parent = extStorageDir + "/2l/Documents/Sent";
                        } else {
                            parent = extStorageDir + "/2l/Documents/Received";
                        }

//                        java.util.Date time2 = new java.util.Date((long)message.getTimestampCreatedLong());
//                        SimpleDateFormat sfd2 = new SimpleDateFormat("yyyymmdd-hhmmss.SSS");
//                        timeStamp = sfd2.format(time2);

                        File file = new File(parent, "DOC-" + timeStamp + message.getExt());

                        if (file.exists()) {

                            String mimeType = getMimeType(parent + "/" + "DOC-" + timeStamp + message.getExt());
                            Uri docURI = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".my.package.name.provider", file);
                            intent.setDataAndType(docURI, mimeType);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            try {
                                context.startActivity(intent);
                            } catch (ActivityNotFoundException e) {
                                Toast.makeText(context, "You may not have a suitable app for viewing this file.", Toast.LENGTH_LONG).show();
                            }
                        }
                        else {
                            Toast.makeText(activity, "Sorry, this document file doesn't exist on your internal storage", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                if (selected_usersList.contains(consolidatedList.get(position)))
                    holder.parent_layout.setBackgroundColor(ContextCompat.getColor(activity.getApplicationContext(), R.color.list_item_selected_state));
                else
                    holder.parent_layout.setBackgroundColor(ContextCompat.getColor(activity.getApplicationContext(), R.color.list_item_normal_state));

                break;

            case TYPE_DATE:
                DateItem dateItem = (DateItem) consolidatedList.get(position);
                DateViewHolder dateViewHolder = (DateViewHolder) viewHolder;

                // Populate date item data here
                dateViewHolder.date.setText(dateItem.getDate());

                break;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setFilter(ArrayList<Object> arrayList) {
        consolidatedList = new ArrayList<>();
        consolidatedList.addAll(arrayList);
        notifyDataSetChanged();
    }

    private void imgLoadedStatus(Message message) {
        mClassDatabaseReference
                .child("imgLoaded")
                .child(String.valueOf(message.getTimestampCreatedLong()))
                .child(mFirebaseAuth.getCurrentUser().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        photoAlreadyLoaded = dataSnapshot.exists();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
    }

    private void docLoadedStatus(Message message) {
        mClassDatabaseReference
                .child("docLoaded")
                .child(String.valueOf(message.getTimestampCreatedLong()))
                .child(mFirebaseAuth.getCurrentUser().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        docAlreadyLoaded = dataSnapshot.exists();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
    }

    private void mapLoadedStatus(Message message) {
        mClassDatabaseReference
                .child("mapLoaded")
                .child(String.valueOf(message.getTimestampCreatedLong()))
                .child(mFirebaseAuth.getCurrentUser().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        mapAlreadyLoaded = dataSnapshot.exists();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
    }

    //PdfiumAndroid (https://github.com/barteksc/PdfiumAndroid)
//https://github.com/barteksc/AndroidPdfViewer/issues/49

    int getPageCount(Uri pdfUri) {
        int pageCount = 0;
        PdfiumCore pdfiumCore = new PdfiumCore(context);
        try {
            //http://www.programcreek.com/java-api-examples/index.php?api=android.os.ParcelFileDescriptor
            ParcelFileDescriptor fd = context.getContentResolver().openFileDescriptor(pdfUri, "r");
            com.shockwave.pdfium.PdfDocument pdfDocument = pdfiumCore.newDocument(fd);
            pageCount = pdfiumCore.getPageCount(pdfDocument);
        } catch (Exception e) {
            //todo with exception
            Log.d(TAG, e.toString());
        }
        return pageCount;
    }

    void generateImageFromPdf(Uri pdfUri) {
        int pageNumber = 0;
        PdfiumCore pdfiumCore = new PdfiumCore(context);
        try {
            //http://www.programcreek.com/java-api-examples/index.php?api=android.os.ParcelFileDescriptor
            ParcelFileDescriptor fd = context.getContentResolver().openFileDescriptor(pdfUri, "r");
            com.shockwave.pdfium.PdfDocument pdfDocument = pdfiumCore.newDocument(fd);
            pdfiumCore.openPage(pdfDocument, pageNumber);
            int width = pdfiumCore.getPageWidthPoint(pdfDocument, pageNumber);
            int height = pdfiumCore.getPageHeightPoint(pdfDocument, pageNumber);
            Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            pdfiumCore.renderPageBitmap(pdfDocument, bmp, pageNumber, 0, 0, width, height);
            saveImage(bmp);
            pdfiumCore.closeDocument(pdfDocument); // important!
        } catch (Exception e) {
            //todo with exception
            Log.d(TAG, e.toString());
        }
    }

    public final static String FOLDER = Environment.getExternalStorageDirectory() + "/2l/PDF";

    private void saveImage(Bitmap bmp) {
        FileOutputStream out = null;
        try {
            File folder = new File(FOLDER);
            if (!folder.exists())
                folder.mkdirs();
            File file = new File(folder, "PDF-" + timeStamp + ".png");
            out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, out); // bmp is your Bitmap instance
        } catch (Exception e) {
            //todo with exception
        } finally {
            try {
                if (out != null)
                    out.close();
            } catch (Exception e) {
                //todo with exception
            }
        }
    }

    public static String getMimeType(String url) {
        String type = null;
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        }
        return type;
    }

    public static String getDocType(String mimeType) {

        String type = "";

        List<String> word = new ArrayList<String>();
        List<String> excel = new ArrayList<String>();
        List<String> powerpoint = new ArrayList<String>();
        List<String> pdf = new ArrayList<String>();
        List<String> text = new ArrayList<String>();

        word.add("application/msword");
        word.add("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        word.add("application/vnd.openxmlformats-officedocument.wordprocessingml.template");

        excel.add("application/vnd.ms-excel");
        excel.add("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        excel.add("application/vnd.openxmlformats-officedocument.spreadsheetml.template");

        powerpoint.add("application/vnd.ms-powerpoint");
        powerpoint.add("application/vnd.openxmlformats-officedocument.presentationml.presentation");
        powerpoint.add("application/vnd.openxmlformats-officedocument.presentationml.template");
        powerpoint.add("application/vnd.openxmlformats-officedocument.presentationml.slideshow");

        pdf.add("application/pdf");

        text.add("text/plain");

        if (word.contains(mimeType)) {
            type = "word";
        } else if (excel.contains(mimeType)) {
            type = "excel";
        } else if (powerpoint.contains(mimeType)) {
            type = "powerpoint";
        } else if (pdf.contains(mimeType)) {
            type = "pdf";
        } else if (text.contains(mimeType)) {
            type = "text";
        }
        return type;
    }
}

