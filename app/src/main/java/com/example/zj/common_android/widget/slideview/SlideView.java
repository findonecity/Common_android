package com.example.zj.common_android.widget.slideview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Scroller;

import com.example.zj.common_android.R;
import com.example.zj.common_android.util.SharedPreferencesManager;
import com.example.zj.common_android.util.glide.GlideImageUtil;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * 自定义轮播图
 * Created by ZhangXiaoWei on 2016/5/30.
 */
@SuppressLint("HandlerLeak")
public class SlideView extends FrameLayout {

    private ImageHandler mhandler = new ImageHandler(new WeakReference<SlideView>(this));
    private List<String> imageUris;
    private Context context;
    private List<ImageView> imageViewsList;
    private List<ImageView> dotViewsList;
    private LinearLayout mLinearLayout;
    private ViewPager mViewPager;
    private SlideViewListener mSlideViewListener;//向外提供接口
    private boolean isTwo = false;
    private int pos;

    public SlideView(Context context) {
        this(context, null);
        this.context = context;
    }

    public SlideView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        this.context = context;
    }

    public SlideView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //读取该自定义控件自定义的属性
        this.context = context;

        int duration = SharedPreferencesManager.getInstance().getIntData(context, "slideTime", "fdwelltime");
        ImageHandler.MSG_DELAY = duration == 0 ? 5000 : duration;
        initUI(context);
        if (!(imageUris.size() <= 0)) {
            setImageUris(imageUris);//
        }
    }

    /**
     * 设置监听
     *
     * @param mSlideViewListener
     */
    public void setOnPageClickListener(SlideViewListener mSlideViewListener) {
        this.mSlideViewListener = mSlideViewListener;
    }


    /**
     * 初始化
     *
     * @param context
     */
    private void initUI(Context context) {
        imageViewsList = new ArrayList<>();
        dotViewsList = new ArrayList<>();
        imageUris = new ArrayList<>();

        LayoutInflater.from(context).inflate(R.layout.widget_slideshow, this, true);
        mLinearLayout = findViewById(R.id.ll_dot);
        mViewPager = findViewById(R.id.viewPager);
        //mFlashViewListener必须实例化
    }

    /**
     * 设置图片开始轮播
     *
     * @param imageuris 数据源
     */
    public void setImageUris(List<String> imageuris) {
        imageUris.clear();
        imageViewsList.clear();
        dotViewsList.clear();
        mLinearLayout.removeAllViews();

        // 如果得到的图片张数为0，则增加一张默认的图片
        if (imageuris.size() <= 0) {
            imageUris.add("drawable://" + R.mipmap.ic_launcher);
        } else {
            if (imageuris.size() == 2) {
                isTwo = true;
                imageUris.addAll(imageuris);
                imageUris.addAll(imageuris);
            } else {
                isTwo = false;
                imageUris.addAll(imageuris);
            }
        }
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                (int) getResources().getDimension(R.dimen.flash_view_dot_size),
                (int) getResources().getDimension(R.dimen.flash_view_dot_size));
        lp.setMargins(5, 0, 0, 0);
        for (int i = 0; i < imageUris.size(); i++) {
            ImageView imageView = new ImageView(getContext());
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);// X和Y方向都填满
            GlideImageUtil.loadWebImage(context, imageUris.get(i), imageView);

            imageView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            mhandler.sendEmptyMessage(ImageHandler.MSG_KEEP_SILENT);
                            break;
                        case MotionEvent.ACTION_UP:
                            if (imageUris.size() > 1) {
                                mhandler.sendEmptyMessageDelayed(ImageHandler.MSG_UPDATE_IMAGE,
                                        ImageHandler.MSG_DELAY);
                            }
                            break;
                    }
                    return false;
                }
            });

            imageViewsList.add(imageView);
            ImageView viewDot = new ImageView(getContext());
            if (i == 0) {
                viewDot.setBackgroundResource(R.drawable.dot_checked);
            } else {
                viewDot.setBackgroundResource(R.drawable.dot_unchecked);
            }
            viewDot.setLayoutParams(lp);

            //为两张图片时加入的判断
            if (isTwo) {
                if (i > 1) {

                } else {
                    dotViewsList.add(viewDot);
                    mLinearLayout.addView(viewDot);
                }
            } else {
                dotViewsList.add(viewDot);
                mLinearLayout.addView(viewDot);
            }

        }
        mViewPager.setFocusable(true);
        mViewPager.setAdapter(new MyPagerAdapter());
        mViewPager.addOnPageChangeListener(new MyPageChangeListener());

        //图片小于等于1张时，不轮播
        if (imageUris.size() > 1) {
            // 利用反射修改自动轮播的动画持续时间
            try {

                Field field = ViewPager.class.getDeclaredField("mScroller");

                field.setAccessible(true);

                FixedSpeedScroller scroller = new FixedSpeedScroller(
                        mViewPager.getContext(), new AccelerateInterpolator());

                field.set(mViewPager, scroller);

                int duration = SharedPreferencesManager.getInstance().getIntData(context, "slideTime", "fsliptime");
                scroller.setmDuration(duration == 0 ? 150 : duration);//动画的持续时间

                mViewPager.setCurrentItem(100 * imageViewsList.size());

                mhandler.sendEmptyMessageDelayed(ImageHandler.MSG_UPDATE_IMAGE,
                        ImageHandler.MSG_DELAY);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 切换轮播小点的显示
     *
     * @param selectItems
     */
    private void setImageBackground(int selectItems) {
        for (int i = 0; i < dotViewsList.size(); i++) {
            if (i == selectItems % dotViewsList.size()) {
                dotViewsList.get(i).setBackgroundResource(R.drawable.dot_checked);
            } else {
                dotViewsList.get(i).setBackgroundResource(R.drawable.dot_unchecked);
            }
        }
    }

    /**
     * 数据适配器
     */
    private class MyPagerAdapter extends PagerAdapter {

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            position = position % imageViewsList.size();


            if (position < 0) {
                position = position + imageViewsList.size();

            }
            if (isTwo) {
                pos = position % 2;
            } else {
                pos = position;
            }
            final int posclick = pos;
            View view = imageViewsList.get(position);
//            view.setTag(position);
            view.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (mSlideViewListener != null) {
                        mSlideViewListener.onClick(posclick);
                    } else {

                    }

                }
            });
            ViewParent vp = view.getParent();
            if (vp != null) {
                ViewPager pager = (ViewPager) vp;
                pager.removeView(view);
            }
            container.addView(view);
            return view;
        }

        @Override
        public int getCount() {
            if (imageUris.size() <= 1) {
                return 1;
            } else {
                return Integer.MAX_VALUE;
            }

        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }
    }

    private class MyPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int arg0) {

            switch (arg0) {
                case ViewPager.SCROLL_STATE_DRAGGING:// 正在拖动页面时执行此处
                    mhandler.sendEmptyMessage(ImageHandler.MSG_KEEP_SILENT);
                    break;
                case ViewPager.SCROLL_STATE_IDLE:// 未拖动页面时执行此处
                    mhandler.sendEmptyMessageDelayed(ImageHandler.MSG_UPDATE_IMAGE, ImageHandler.MSG_DELAY);
                    break;
                default:
                    break;
            }

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageSelected(int pos) {
            mhandler.sendMessage(Message.obtain(mhandler, ImageHandler.MSG_PAGE_CHANGED, pos, 0));

            setImageBackground(pos);

        }

    }

    @SuppressWarnings("unused")
    private void destoryBitmaps() {
        for (int i = 0; i < imageViewsList.size(); i++) {
            ImageView imageView = imageViewsList.get(i);
            Drawable drawable = imageView.getDrawable();
            if (drawable != null) {
                drawable.setCallback(null);
            }
        }
    }


    /**
     * FixedSpeedScroller类
     * 利用反射修改自动轮播的动画持续时间
     */
    public class FixedSpeedScroller extends Scroller {
        private int mDuration = 1000;

        public FixedSpeedScroller(Context context) {
            super(context);
        }

        public FixedSpeedScroller(Context context, Interpolator interpolator) {
            super(context, interpolator);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy, int duration) {

            super.startScroll(startX, startY, dx, dy, mDuration);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy) {

            super.startScroll(startX, startY, dx, dy, mDuration);
        }

        public void setmDuration(int time) {
            mDuration = time;
        }

        public int getmDuration() {
            return mDuration;
        }
    }

    private static class ImageHandler extends android.os.Handler {

        protected static final int MSG_UPDATE_IMAGE = 1;

        protected static final int MSG_KEEP_SILENT = 2;

        protected static final int MSG_BREAK_SILENT = 3;

        protected static final int MSG_PAGE_CHANGED = 4;

        protected static int MSG_DELAY = 5000;//5秒开始轮播到下一张图片

        private WeakReference<SlideView> weakReference;//弱引用，防止内存泄漏
        private int currentItem = 0;

        protected ImageHandler(WeakReference<SlideView> wk) {
            weakReference = wk;
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            SlideView activity = weakReference.get();
            if (activity == null) {
                return;
            }
            if (activity.mhandler.hasMessages(MSG_UPDATE_IMAGE)) {
                // 这里必须加入currentItem>0的判断，否则不能完美的自动轮播
                if (currentItem > 0) {
                    activity.mhandler.removeMessages(MSG_UPDATE_IMAGE);
                }
            }
            switch (msg.what) {
                case MSG_UPDATE_IMAGE:
                    currentItem++;
                    activity.mViewPager.setCurrentItem(currentItem);
                    activity.mhandler.sendEmptyMessageDelayed(MSG_UPDATE_IMAGE, MSG_DELAY);
                    break;
                case MSG_KEEP_SILENT:
                    break;
                case MSG_BREAK_SILENT:
                    activity.mhandler.sendEmptyMessageDelayed(MSG_UPDATE_IMAGE, MSG_DELAY);
                    break;
                case MSG_PAGE_CHANGED:
                    currentItem = msg.arg1;
                    activity.mhandler.sendEmptyMessageDelayed(MSG_UPDATE_IMAGE, MSG_DELAY);
                    break;
                default:
                    break;
            }
        }
    }

}
