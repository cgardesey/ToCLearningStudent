/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.prepeez.toclearningstudent.util;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;

import com.prepeez.toclearningstudent.R;

/**
 * ColorUtils is a class with one method, used to color the ViewHolders in
 * the RecyclerView. I put in a separate class in an attempt to keep the
 * code organized.
 *
 * We aren't going to go into detail about how this method works, but feel
 * free to explore!
 */
public class ColorUtils {

    public static int getViewHolderBackgroundColorFromInstance(Context context, int instanceNum) {
        switch (instanceNum) {
            case 0:
                return ContextCompat.getColor(context, R.color.orange00);
            case 1:
                return ContextCompat.getColor(context, R.color.orange01);
            case 2:
                return ContextCompat.getColor(context, R.color.orange02);
            case 3:
                return ContextCompat.getColor(context, R.color.orange03);
            case 4:
                return ContextCompat.getColor(context, R.color.orange04);
            case 5:
                return ContextCompat.getColor(context, R.color.orange05);
            case 6:
                return ContextCompat.getColor(context, R.color.orange06);
            case 7:
                return ContextCompat.getColor(context, R.color.orange07);
            case 8:
                return ContextCompat.getColor(context, R.color.orange08);
            case 9:
                return ContextCompat.getColor(context, R.color.orange09);

            default:
                return Color.WHITE;
        }
    }
}
