package com.cgessinger.creaturesandbeasts.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;
import net.neoforged.api.distmarker.Dist;

public class MinipadFlowerParticle extends SingleQuadParticle {
    private final SpriteSet sprites;

    public MinipadFlowerParticle(ClientLevel level, double x, double y, double z, double motionX, double motionY, double motionZ, SpriteSet sprites, RandomSource random) {
        super(level, x, y, z, sprites.get(random));
        this.xd = motionX;
        this.yd = motionY * 0.2F;
        this.zd = motionZ;
        this.quadSize *= 0.5F;
        this.lifetime = 10;
        this.sprites = sprites;
        this.setSpriteFromAge(this.sprites);
    }

    @Override
    public void tick() {
        super.tick();
        this.setSpriteFromAge(this.sprites);
    }

    @Override
    protected SingleQuadParticle.Layer getLayer() {
        return SingleQuadParticle.Layer.OPAQUE;
    }

    @Override
    public int getLightCoords(float tick) {
        int i = super.getLightCoords(tick);
        int k = i >> 16 & 255;
        return 240 | k << 16;
    }
    public static class Factory implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public Factory(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Override
        public Particle createParticle(SimpleParticleType typeIn, ClientLevel levelIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, RandomSource random) {
            return new MinipadFlowerParticle(levelIn, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet, random);
        }
    }
}