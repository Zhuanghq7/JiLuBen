package SwipeHelper;

import android.app.Activity;
import android.view.MotionEvent;

/**
 * Created by liufengkai on 2016/10/5.
 */

public class SwipeUtils {

    public static boolean dispatchSwipeEvent(Activity activity,
                                             SwipeHelper helper,
                                             SwipeWindowHelper mSwipeWindowHelper,
                                             MotionEvent ev) {
        if (!helper.supportSlideBack()) {
            return activity.dispatchTouchEvent(ev);
        }

        if (mSwipeWindowHelper == null) {
            mSwipeWindowHelper = new SwipeWindowHelper(activity.getWindow());
        }
        return mSwipeWindowHelper.processTouchEvent(ev) || activity.dispatchTouchEvent(ev);
    }
}
