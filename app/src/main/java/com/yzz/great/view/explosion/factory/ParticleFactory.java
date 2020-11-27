package com.yzz.great.view.explosion.factory;

import android.graphics.Bitmap;
import android.graphics.Rect;
import com.yzz.great.view.explosion.particle.Particle;

/**
 * 粒子工程
 */
public abstract class ParticleFactory {
    /**
     * 生成粒子
     *
     * @param bitmap bitmap
     * @param bound bound
     * @return 粒子数组
     */
    public abstract Particle[][] generateParticles(Bitmap bitmap, Rect bound);
}
