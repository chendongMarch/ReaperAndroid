package com.march.reaper.iview.dialog;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.march.lib.adapter.common.SimpleItemListener;
import com.march.lib.adapter.core.BaseViewHolder;
import com.march.lib.adapter.core.SimpleRvAdapter;
import com.march.lib.core.dialog.BaseDialog;
import com.march.reaper.R;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Project  : Reaper
 * Package  : com.march.reaper.iview.dialog
 * CreateAt : 2016/11/23
 * Describe :
 *
 * @author chendong
 */

public class MenuDialog extends BaseDialog {

    private SimpleRvAdapter<MyMenu> mMenuAdapter;
    private TextView mTitleTv;

    public static class MyMenu {
        public String desc;
        public int id;

        public MyMenu(int id, String desc) {
            this.id = id;
            this.desc = desc;
        }
    }

    public interface OnMyMenuItemClickListener {
        void onClick(MenuDialog dialog, int pos, View view, MyMenu menu);
    }

    private OnMyMenuItemClickListener mListener;

    private RecyclerView mMenuRv;

    private List<MyMenu> mMenus;

    private String mTitle;

    public MenuDialog(Context context, String title, List<MyMenu> mMenus, OnMyMenuItemClickListener listener) {
        super(context, R.style.dialog_theme);
        this.mTitle = title;
        this.mMenus = mMenus;
        this.mListener = listener;
    }


    @Override
    protected void initViews() {
        ButterKnife.bind(this);
        mTitleTv = getView(R.id.menu_title);
        mMenuRv = getView(R.id.rv_menu);
        getWindow().setWindowAnimations(R.style.dialog_anim_bottom_to_center);
    }

    @OnClick({R.id.menu_title, R.id.menu_bot})
    public void clickBtn(View view) {
        switch (view.getId()) {
            case R.id.menu_title:
                break;
            case R.id.menu_bot:
                dismiss();
                break;
        }
    }

    @Override
    public void show() {
        if (mMenuAdapter == null) {
            mTitleTv.setText(mTitle);
            mMenuAdapter = new SimpleRvAdapter<MyMenu>(getContext(), mMenus, R.layout.dialog_menu_item) {
                @Override
                public void onBindView(BaseViewHolder holder, MyMenu data, int pos, int type) {
                    holder.setText(R.id.item_menu_tv, data.desc);
                }
            };
            mMenuAdapter.setItemListener(new SimpleItemListener<MyMenu>() {
                @Override
                public void onClick(int pos, BaseViewHolder holder, MyMenu data) {
                    if (mListener != null) {
                        mListener.onClick(MenuDialog.this, pos, holder.getParentView(), data);
                        dismiss();
                    }
                }
            });
            mMenuRv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
            mMenuRv.setAdapter(mMenuAdapter);
        } else {
            mMenuAdapter.notifyDataSetChanged();
        }
        super.show();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_menu;
    }

    @Override
    protected void setWindowParams() {
        setWindowParams(MATCH, WRAP, 1f, 0.5f, Gravity.BOTTOM);
    }
}
