package open.wow.aaron.com.eyepetizer.framework;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * 作者：哇牛Aaron
 * 作者简书文章地址: http://www.jianshu.com/users/07a8b5386866/latest_articles
 * 时间: 2017/7/20
 * 功能描述: viewPager适配器
 */

/**
 * 问题一
 * 使用 FragmentPagerAdapter 时，ViewPager 中的所有 Fragment 实例常驻内存，
 * 当 Fragment 变得不可见时仅仅是视图结构的销毁，即调用了 onDestroyView 方法。
 * 由于 FragmentPagerAdapter 内存消耗较大，所以适合少量静态页面的场景。
 * <p>
 * 使用 FragmentStatePagerAdapter 时，
 * 当 Fragment 变得不可见，不仅视图层次销毁，实例也被销毁，
 * 即调用了 onDestroyView 和 onDestroy 方法，仅仅保存 Fragment 状态。
 * 相比而言， FragmentStatePagerAdapter 内存占用较小，所以适合大量动态页面，比如我们常见的新闻列表类应用。
 */
public class OpenEyesViewPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> mFragmentList;

    public OpenEyesViewPagerAdapter(FragmentManager supportFragmentManager, List<Fragment> fragmentList) {
        super(supportFragmentManager);
        this.mFragmentList = fragmentList;
    }



//    public OpenEyesViewPagerAdapter(FragmentManager fm, List<Fragment> mFragmentList) {
//        super(fm);
//        this.mFragmentList = mFragmentList;
//    }




    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }
}
