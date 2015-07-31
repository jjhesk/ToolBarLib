package com.hkm.advancedtoolbar.V3;

import com.hkm.advancedtoolbar.R;

/**
 * Created by hesk on 31/7/15.
 */
public enum LayoutAsset {
    classic_1(R.layout.material_search_ios_classic),
    classic_2(R.layout.material_search_ios),
    classic_3(R.layout.material_search_ios_simple),
    i_logo_ii(R.layout.i_t_ii),
    i_logo_ir(R.layout.i_t_ir),
    material(R.layout.search_material);

    private final int id;

    LayoutAsset(int id) {
        this.id = id;
    }

    public int getResourceId() {
        return id;
    }
}
