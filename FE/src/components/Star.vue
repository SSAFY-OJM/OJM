<template>
  <div>
    <!-- 별점 선택 -->
    <div class="rating">
      <label v-for="index in 5" :key="index" :for="`star${index}`" class="rating__label" @click="selectRating(index)">
        <input type="radio" class="rating__input" :id="`star${index}`" v-model="selectedRating" :value="index" />
        <span class="star-icon"></span>
      </label>
    </div>

    <!-- 선택된 별점 확인 -->
    <div v-if="selectedRating !== 0">
      <p>선택된 별점: {{ selectedRating }}점</p>
    </div>
  </div>
  <star></star>
</template>

<script setup>
import { ref } from 'vue';
import { star } from 'Star.vue';
const selectedRating = ref(0);

const selectRating = (rating) => {
  selectedRating.value = rating;
};
</script>

<style scoped>
/* 별점 스타일링 */
.rating__input {
  display: none;
  /* 라디오버튼 숨기기 */
}

.rating__label {
  width: 24px;
  height: 24px;
  display: inline-block;
  cursor: pointer;
}

.star-icon {
  width: 24px;
  height: 24px;
  display: inline-block;
  background-image: url("../assets/images/stars.svg");
  background-repeat: no-repeat;
}

/* 선택된 별점 스타일링 */
.rating__label--full .star-icon {
  background-position: right;
}
</style>