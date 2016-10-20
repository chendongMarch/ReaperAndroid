package com.march.reaper.helper;

import com.march.reaper.common.Constant;

import java.util.List;

/**
 * Project  : Reaper
 * Package  : com.march.reaper.helper
 * CreateAt : 2016/10/13
 * Describe :
 *
 * @author chendong
 */

public class PageLoadHelper<D> {
    protected static final int mPreLoadNum = Constant.PRE_LOAD_NUM;
    protected int offset = 0, limit = Constant.ONECE_QUERY_DATA_NUM;
    protected boolean isLoadEnd = true;
    protected List<D> datas;
}
