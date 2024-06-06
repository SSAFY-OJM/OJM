<template>
  <UserMenu />
  <!-- <TimeTable /> -->
</template>

<script setup>
import UserMenu from "@/components/UserMenu.vue";
import TimeTable from "@/components/TimeTable copy2.vue";
// MATTERMOST =============================
const mattermost = () => {
  axios
    .post(
      "https://meeting.ssafy.com/hooks/wkzwo7rf1p87mbygh15fsebjmy",

      {
        channel: "Menu",
        text: "민지민지민지민지민지",
      }
    )
    .then((res) => console.log(res))
    .catch((e) => console.log(e));
};
</script>

<style scoped></style>
