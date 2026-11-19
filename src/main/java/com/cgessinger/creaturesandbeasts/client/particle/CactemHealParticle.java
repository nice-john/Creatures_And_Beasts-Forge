package com.cgessinger.creaturesandbeasts.client.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;

public class CactemHealParticle extends SingleQuadParticle {
    private final SpriteSet sprites;

    public CactemHealParticle(ClientLevel level, double x, double y, double z, double motionX, double motionY, double motionZ, SpriteSet sprites) {
        super(level, x, y, z, sprites.first());
        this.xd = motionX;
        this.yd = motionY;
        this.zd = motionZ;
        this.quadSize *= level.getRandom().nextDouble() * 0.6D + 0.4D;
        this.lifetime = level.getRandom().nextInt(15) + 20;
        this.sprites = sprites;
        this.setSpriteFromAge(this.sprites);
    }

    @Override
    public void tick() {
        super.tick();
        this.yd += level.getRandom().nextDouble() * 0.01D;
        this.setSpriteFromAge(this.sprites);
    }

    @Override
    protected SingleQuadParticle.Layer getLayer() {
        return SingleQuadParticle.Layer.TRANSLUCENT;
    }

    @Environment(EnvType.CLIENT)
    public static class Factory implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public Factory(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Override
        public Particle createParticle(SimpleParticleType typeIn, ClientLevel levelIn, double x, double y, double z,
                                       double xSpeed, double ySpeed, double zSpeed, RandomSource random) {
            return new CactemHealParticle(levelIn, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
        }
    }
}