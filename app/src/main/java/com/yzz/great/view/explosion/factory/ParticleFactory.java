package com.yzz.great.view.explosion.factory;

import android.graphics.Bitmap;
import android.graphics.Rect;
import com.yzz.great.view.explosion.particle.Particle;


public abstract class ParticleFactory {
    public abstract Particle[][] generateParticles(Bitmap bitmap, Rect bound);
}
