accessWidener v2 named

# MeleeAttackGoal.ticksUntilNextAttack – used by entity AI code
accessible field net/minecraft/world/entity/ai/goal/MeleeAttackGoal ticksUntilNextAttack I

# SpawnEggItem.BY_ID – used by custom spawn-egg subclasses
accessible field net/minecraft/world/item/SpawnEggItem BY_ID Ljava/util/Map;

# CindershellEntity needs to close any open furnace menu when riders dismount.
accessible method net/minecraft/world/entity/player/Player closeContainer ()V

# CNBParticleTypes registers SimpleParticleType instances directly via its
# (boolean overrideLimiter) constructor; vanilla makes it protected.
accessible method net/minecraft/core/particles/SimpleParticleType <init> (Z)V
