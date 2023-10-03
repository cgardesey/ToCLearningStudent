package com.prepeez.toclearningstudent.adapter;

import android.app.Activity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.prepeez.toclearningstudent.R;

import hani.momanii.supernova_emoji_library.Helper.EmojiconTextView;

/**
 * Created by 2CLearning on 2/8/2018.
 */

public class MessageViewHolder extends RecyclerView.ViewHolder {
    RelativeLayout parent_layout;
    LinearLayout layout;

    TextView name;

    LinearLayout txtMsgFrame;
    EmojiconTextView txtMsg;
    FrameLayout linkPrevFrame;
    LinearLayout linkPrevFrame2;
    ImageView linkImg;
    LinearLayout linkTextArea;
    TextView linkTitle;
    TextView linkDesc;
    ImageView close;

    RelativeLayout picFrame;
    ImageView image;
    RelativeLayout downloadStatusWrapper_pic;
    ProgressBar pbar_pic;
    ImageView uploadImg_pic;
    CardView retry;
    TextView retry_text;

    LinearLayout docFrame;
    RelativeLayout doc;
    ImageView docIcon;
    TextView docTitle;
    ImageView pdfImg;

    RelativeLayout downloadStatusWrapper_doc;
    ImageView uploadImg_doc;
    ProgressBar pbar_doc;
    TextView time;
    ImageView statusImg;
    TextView metaData;

    public MessageViewHolder(View v, Activity activity) {
        super(v);

        parent_layout = itemView.findViewById(R.id.bubble_layout_parent);
        layout = itemView.findViewById(R.id.bubble_layout);

        name = itemView.findViewById(R.id.nameTextView);

        txtMsgFrame = itemView.findViewById(R.id.txt_msg_frame);
        txtMsg = itemView.findViewById(R.id.txt_msg);

        linkPrevFrame = itemView.findViewById(R.id.link_prev_frame);
        linkPrevFrame2 = itemView.findViewById(R.id.link_prev_frame2);
        linkImg = itemView.findViewById(R.id.link_img);
        linkTextArea = itemView.findViewById(R.id.link_text_area);
        linkTitle = itemView.findViewById(R.id.link_title);
        linkDesc = itemView.findViewById(R.id.link_desc);
        close = itemView.findViewById(R.id.close);

        picFrame = itemView.findViewById(R.id.pic_frame);
        image = itemView.findViewById(R.id.photoImageView);
        downloadStatusWrapper_pic = itemView.findViewById(R.id.downloadStatusWrapper_pic);
        pbar_pic = itemView.findViewById(R.id.pbar_pic);
        uploadImg_pic = itemView.findViewById(R.id.uploadImg_pic);
        retry = itemView.findViewById(R.id.retry);
        retry_text = itemView.findViewById(R.id.retry_text);

        docFrame = itemView.findViewById(R.id.doc_frame);
        doc = itemView.findViewById(R.id.doc);
        docIcon = itemView.findViewById(R.id.docIcon);
        docTitle = itemView.findViewById(R.id.docTitle);
        downloadStatusWrapper_doc = itemView.findViewById(R.id.downloadStatusWrapper_doc);
        uploadImg_doc = itemView.findViewById(R.id.uploadImg_doc);
        pbar_doc = itemView.findViewById(R.id.pbar);
        pdfImg = itemView.findViewById(R.id.pdf_img);

        time = itemView.findViewById(R.id.time);
        statusImg = itemView.findViewById(R.id.statusImg);
        metaData = itemView.findViewById(R.id.metaData);
    }
}
