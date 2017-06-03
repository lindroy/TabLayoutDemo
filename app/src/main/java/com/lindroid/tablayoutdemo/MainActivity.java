package com.lindroid.tablayoutdemo;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private List<Fragment> fragments = new ArrayList<>();
    private List<String> tabs = new ArrayList<>(); //标签名称
    private List<Integer> tabNumbers = new ArrayList<>(); //信息数目
    private ViewHolder holder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initView();
    }

    private void initData() {
        tabs.add("新消息" );
        tabs.add("朋友圈");
        tabs.add("公众号");
        tabNumbers.add(999);
        tabNumbers.add(99);
        tabNumbers.add(9);
        fragments.add(new TabFragment(this,tabs.get(0)));
        fragments.add(new TabFragment(this,tabs.get(1)));
        fragments.add(new TabFragment(this,tabs.get(2)));
    }

    private void initView() {
        tabLayout = (TabLayout) findViewById(R.id.tayLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        //设置TabLayout的模式
        tabLayout.setTabMode(TabLayout.MODE_FIXED);

        //设置分割线
        LinearLayout linearLayout = (LinearLayout) tabLayout.getChildAt(0);
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        linearLayout.setDividerDrawable(ContextCompat.getDrawable(this,
                R.drawable.divider)); //设置分割线的样式
        linearLayout.setDividerPadding(dip2px(10)); //设置分割线间隔

        viewPager.setAdapter(new TabAdapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);

        setTabView();
    }

    /**
     * 设置Tab的样式
     */
    private void setTabView() {
        holder = null;
        for (int i = 0; i < tabs.size(); i++) {
            //依次获取标签
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            //为每个标签设置布局
            tab.setCustomView(R.layout.tab_item);
            holder = new ViewHolder(tab.getCustomView());
            //为标签填充数据
            holder.tvTabName.setText(tabs.get(i));
            holder.tvTabNumber.setText(String.valueOf(tabNumbers.get(i)));
            //默认选择第一项
            if (i == 0){
                holder.tvTabName.setSelected(true);
                holder.tvTabNumber.setSelected(true);
                holder.tvTabName.setTextSize(18);
                holder.tvTabNumber.setTextSize(18);
            }
        }

        //tab选中的监听事件
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                holder = new ViewHolder(tab.getCustomView());
                holder.tvTabName.setSelected(true);
                holder.tvTabNumber.setSelected(true);
                //选中后字体变大
                holder.tvTabName.setTextSize(18);
                holder.tvTabNumber.setTextSize(18);
                //让Viewpager跟随TabLayout的标签切换
                viewPager.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                holder = new ViewHolder(tab.getCustomView());
                holder.tvTabName.setSelected(false);
                holder.tvTabNumber.setSelected(false);
                //恢复为默认字体大小
                holder.tvTabName.setTextSize(16);
                holder.tvTabNumber.setTextSize(16);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    class ViewHolder{
        TextView tvTabName;
        TextView tvTabNumber;

        public ViewHolder(View tabView) {
            tvTabName = (TextView) tabView.findViewById(R.id.tv_tab_name);
            tvTabNumber = (TextView) tabView.findViewById(R.id.tv_tab_number);
        }
    }

    class TabAdapter extends FragmentPagerAdapter{

        public TabAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        //显示标签上的文字
        @Override
        public CharSequence getPageTitle(int position) {
            return tabs.get(position);
        }

//        @Override
//        public CharSequence getPageTitle(int position) {
//            return tabs.get(position % tabs.size());
//        }
    }

    //像素单位转换
    public int dip2px(int dip) {
        float density = getResources().getDisplayMetrics().density;
        return (int) (dip * density + 0.5);
    }
}
