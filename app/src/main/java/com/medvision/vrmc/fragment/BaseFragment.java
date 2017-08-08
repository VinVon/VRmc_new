package com.medvision.vrmc.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.medvision.vrmc.utils.NetworkStat;

/**
 * Created by raytine on 2017/7/6.
 */

public class BaseFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }
    public int checkNetWork() {
        switch (NetworkStat.getNetWorkType(getActivity())){
            case NetworkStat.NETWORKTYPE_INVALID:
                return 0;
            case NetworkStat.NETWORKTYPE_2G:
            case NetworkStat.NETWORKTYPE_3G:
            case NetworkStat.NETWORKTYPE_WAP:
                return 1;
            case NetworkStat.NETWORKTYPE_WIFI:
                return 2;
            default:
                return 0;
        }
    }
}
