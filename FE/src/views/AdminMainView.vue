<template>
  <!-- <button type="button" class="w3-button w3-round w3-gray" v-on:click="fnWrite">
    메뉴업로드
  </button>
  <button type="button" class="w3-button w3-round w3-gray" v-on:click="fnList">
    메뉴목록
  </button> -->
  <AdminMenu />
</template>

<script setup>
// import { useRouter } from "vue-router";
import AdminMenu from "@/components/AdminMenu.vue";

// const router = useRouter();

// const fnWrite = () => {
//   router.push({
//     path: "/admin/board/write", // 절대 경로로 수정
//   });
// };

// const fnList = () => {
//   router.push({
//     path: "/admin/board/list", // 절대 경로로 수정
//   });
// };
</script>

<style scoped></style>
