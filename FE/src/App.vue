<template>
  <NavBarCompo />
  <RouterView />
  <FooterCompo />
</template>

<script setup>
// import { NavBar } from "./components/Navbar.vue";
// import NavBarCompo from "./components/NavBarCompo.vue";
import NavBarCompo from "./components/NavbarComponent.vue";
import FooterCompo from "./components/FooterCompo.vue";
import { RouterView } from "vue-router";
</script>

<style scoped></style>
