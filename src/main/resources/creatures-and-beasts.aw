accessWidener v2 official



# CindershellEntity needs to close any open furnace menu when riders dismount.
accessible method net/minecraft/world/entity/player/Player closeContainer ()V

# CNBParticleTypes registers SimpleParticleType instances directly via its
# (boolean overrideLimiter) constructor; vanilla makes it protected.
accessible method net/minecraft/core/particles/SimpleParticleType <init> (Z)V

# 26.x made MenuScreens.register private; CNBClient binds the cinder furnace screen with it.
accessible method net/minecraft/client/gui/screens/MenuScreens register (Lnet/minecraft/world/inventory/MenuType;Lnet/minecraft/client/gui/screens/MenuScreens$ScreenConstructor;)V
