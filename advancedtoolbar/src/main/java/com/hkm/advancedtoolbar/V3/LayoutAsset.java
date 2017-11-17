package com.hkm.advancedtoolbar.V3;

import com.hkm.advancedtoolbar.R;

/**
 * Created by hesk on 31/7/15.
 */
@Deprecated
public enum LayoutAsset {
    classic_1(R.layout.material_search_ios_classic),
    classic_2(R.layout.material_search_ios),
    classic_3(R.layout.material_search_ios_simple),
    //button title/logo button button
    i_logo_ii(R.layout.i_t_ii),
    //button title/logo button dynamic_button
    i_logo_ir(R.layout.i_t_ir),
    //title/logo button dynamic_button
    i_logo_ir2(R.layout.i_t_ir2),
    material(R.layout.search_material);

    private final int id;

    LayoutAsset(int id) {
        this.id = id;
    }

    public int getResourceId() {
        return id;
    }
}
