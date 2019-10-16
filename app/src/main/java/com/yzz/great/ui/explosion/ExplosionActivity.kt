package com.yzz.great.ui.explosion

import android.os.Bundle
import com.yzz.great.R
import com.yzz.great.base.BaseActivity
import com.yzz.great.view.explosion.ExplosionField
import com.yzz.great.view.explosion.factory.*
import kotlinx.android.synthetic.main.activity_explosion.*

/**
 * 粒子爆炸
 */
class ExplosionActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_explosion)

        ExplosionField(this, BooleanFactory()).addListener(googleIv)
        ExplosionField(this, ExplodeParticleFactory()).addListener(qqIv)
        ExplosionField(this, FallingParticleFactory()).addListener(qqMusicIv)
        ExplosionField(this, FlyawayFactory()).addListener(qzoneIv)
        ExplosionField(this, InnerFallingParticleFactory()).addListener(vxIv)
        ExplosionField(this, VerticalAscentFactory()).addListener(wbIv)
    }
}