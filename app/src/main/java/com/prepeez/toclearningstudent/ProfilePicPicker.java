package com.prepeez.toclearningstudent;

import android.app.Activity;
import android.net.Uri;
import android.widget.RelativeLayout;

import com.kbeanie.multipicker.api.entity.ChosenImage;
import com.kbeanie.multipicker.api.entity.ChosenVideo;
import com.noelchew.multipickerwrapper.library.MultiPickerWrapper;
import com.noelchew.multipickerwrapper.library.ui.MultiPickerWrapperSupportFragment;

import java.io.File;
import java.util.List;


/**
 * Created by Nana on 10/22/2017.
 */

public class ProfilePicPicker extends MultiPickerWrapperSupportFragment {
    Activity activity;
    RelativeLayout gal, cam, rem;

//    @Override
//    public Dialog onCreateDialog(final Bundle savedInstanceState) {
//        LayoutInflater inflater = getActivity().getLayoutInflater();
//        final View view = inflater.inflate(R.layout.profile_pic_layout,null);
//        gal = view.findViewById(R.id.gal);
//        cam = view.findViewById(R.id.cam);
//        rem = view.findViewById(R.id.rem);
//
//        gal.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // configure cropping activity UI to match current theme colour
//                UCrop.Options options = new UCrop.Options();
//                options.setStatusBarColor(ContextCompat.getColor(activity, R.color.colorPrimaryDark));
//                options.setToolbarColor(ContextCompat.getColor(activity, R.color.colorPrimary));
//                options.setCropFrameColor(ContextCompat.getColor(activity, R.color.colorPrimaryDark));
//                options.setCropFrameStrokeWidth(PixelUtil.dpToPx(activity, 4));
//                options.setCropGridColor(ContextCompat.getColor(activity, R.color.colorPrimary));
//                options.setCropGridStrokeWidth(PixelUtil.dpToPx(activity, 2));
//                options.setActiveWidgetColor(ContextCompat.getColor(activity, R.color.colorPrimary));
//                options.setToolbarTitle("Pick Image and Crop");
//
//                // set rounded cropping guide
//                options.setCircleDimmedLayer(true);
//
//                // set aspectRatioWidth and Height of 1 -> gives square rounded image cropping
//                multiPickerWrapper.getPermissionAndPickSingleImageAndCrop(options, 1, 1);
//            }
//        });
//
//        cam.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                UCrop.Options options = new UCrop.Options();
//                options.setStatusBarColor(ContextCompat.getColor(activity, R.color.colorPrimaryDark));
//                options.setToolbarColor(ContextCompat.getColor(activity, R.color.colorPrimary));
//                options.setCropFrameColor(ContextCompat.getColor(activity, R.color.colorPrimaryDark));
//                options.setCropFrameStrokeWidth(PixelUtil.dpToPx(activity, 4));
//                options.setCropGridColor(ContextCompat.getColor(activity, R.color.colorPrimary));
//                options.setCropGridStrokeWidth(PixelUtil.dpToPx(activity, 2));
//                options.setActiveWidgetColor(ContextCompat.getColor(activity, R.color.colorPrimary));
//                options.setToolbarTitle("Take Picture and Crop");
//
//                // set rounded cropping guide
//                options.setCircleDimmedLayer(true);
//
//                multiPickerWrapper.getPermissionAndTakePictureAndCrop(options, 1, 1);
//            }
//        });
//
//        rem.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//        builder.setView(view);
//      //  builder.setCancelable(false);
//        return builder.create();
//    }

    @Override
    protected MultiPickerWrapper.PickerUtilListener getMultiPickerWrapperListener() {
        return multiPickerWrapperListener;
    }


    MultiPickerWrapper.PickerUtilListener multiPickerWrapperListener = new MultiPickerWrapper.PickerUtilListener() {
        @Override
        public void onPermissionDenied() {
            // do something here
        }

        @Override
        public void onImagesChosen(List<ChosenImage> list) {
            String imagePath = list.get(0).getOriginalPath();
        }

        @Override
        public void onVideosChosen(List<ChosenVideo> list) {
            String filePath = list.get(0).getOriginalPath();
            String thumbnailPath = list.get(0).getPreviewThumbnail();
            Uri uri = Uri.fromFile(new File(filePath));
            // do something with filePath or uri
        }

        @Override
        public void onError(String s) {
            //Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
        }
    };
}