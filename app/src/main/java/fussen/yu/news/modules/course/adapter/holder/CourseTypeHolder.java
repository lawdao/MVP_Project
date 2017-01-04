package fussen.yu.news.modules.course.adapter.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import fussen.yu.news.base.adapter.BaseHolder;
import fussen.yu.news.R;
import fussen.yu.news.modules.course.bean.CourseData;
import fussen.yu.news.utils.UiUtils;

/**
 * Created by Fussen on 2016/12/28.
 */

public class CourseTypeHolder extends BaseHolder<CourseData.DataListUser> {
    @BindView(R.id.image_bg)
    ImageView imageBg;
    @BindView(R.id.text_name)
    TextView textName;
    @BindView(R.id.text_content)
    TextView textContent;

    @Override
    public View initView() {


        View view = View.inflate(UiUtils.getContext(), R.layout.item_course_type, null);

        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void refreshView() {

        Glide.with(UiUtils.getContext())
                .load(mData.imageUrl)
                .error(R.drawable.ic_image_error)
                .placeholder(R.drawable.ic_image_error)
                .into(imageBg);
        textName.setText(mData.categoryName);
    }
}
