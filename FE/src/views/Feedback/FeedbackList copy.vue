<template>
  <div class="board-list flex justify-center items-center flex-col mt-10">
    <div class="boardList">
      <div class="filters flex gap-2">
        <input
          class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
          type="text"
          placeholder="메뉴명"
          v-model="searchMenu"
        />

        <input
          class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
          type="text"
          placeholder="후기 내용"
          v-model="searchContent"
        />

        <button
          class="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm w-full sm:w-auto px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"
          @click="applyFilters"
        >
          검색
        </button>
      </div>
      <table
        class="w3-table-all"
        style="text-align: center; width: max-content"
      >
        <thead>
          <tr>
            <th>No</th>
            <th style="width: 110px">메뉴 날짜</th>
            <th>메뉴종류</th>
            <th>메뉴명</th>
            <th>후기내용</th>
            <th>별점</th>
            <th style="width: 200px">등록일시</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(comment, index) in paginatedComments" :key="comment.id">
            <td>{{ index + 1 + 10 * (currentPage - 1) }}</td>
            <td>{{ splitDate(comment.menuDate) }}</td>
            <td>{{ titles[comment.type] }}</td>
            <td v-html="getFirstLine(replaceBr(comment.menu))"></td>
            <td>{{ comment.content }}</td>
            <td v-html="convertToStars(comment.rate)"></td>
            <td>{{ splitHour(comment.commentDate) }}</td>
          </tr>
        </tbody>
      </table>
    </div>
    <div class="pagination">
      <button @click="changePage(currentPage - 1)" :disabled="currentPage <= 1">
        이전
      </button>
      <span>페이지 {{ currentPage }} / {{ totalPages }}</span>
      <button
        @click="changePage(currentPage + 1)"
        :disabled="currentPage >= totalPages"
      >
        다음
      </button>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from "vue";
import { useRoute } from "vue-router";
import axios from "axios";

const route = useRoute(); // 라우트 쿼리 파라미터 가져오기
const comments = ref([]); // 피드백 리스트를 넣을 배열

const replaceBr = (e) => {
  return e.replace(/\\r\\n|\\n|\\r/gm, "<br>");
};

const getFirstLine = (e) => {
  return e.split("<br>")[0];
};

const convertToStars = (rate) => {
  return "⭐".repeat(rate) + "✰".repeat(5 - rate);
};

const titles = [
  "20F 한식",
  "20F 일품",
  "10F 도시락",
  "10F 샌드위치 & 샐러드",
  "10F 샐러드",
];

const searchMenu = ref("");
const searchContent = ref("");
const currentPage = ref(1);
const commentsPerPage = 10;

const filteredComments = ref([]);

const applyFilters = () => {
  filteredComments.value = comments.value
    .filter((comment) => {
      return (
        (searchMenu.value === "" || comment.menu.includes(searchMenu.value)) &&
        (searchContent.value === "" ||
          comment.content.includes(searchContent.value))
      );
    })
    .reverse();
  currentPage.value = 1; // 필터를 새로 적용하면 페이지를 1로 초기화
};

const totalPages = computed(() => {
  return Math.ceil(filteredComments.value.length / commentsPerPage);
});

const paginatedComments = computed(() => {
  const start = (currentPage.value - 1) * commentsPerPage;
  const end = start + commentsPerPage;
  return filteredComments.value.slice(start, end);
});

const changePage = (page) => {
  if (page < 1 || page > totalPages.value) {
    return;
  }
  currentPage.value = page;
};

/**
 * yyyy-mm-dd 형식으로 잘라주는 함수
 *
 */
const splitDate = (ele) => ele.substr(0, 10);

const splitHour = (ele) => ele.slice(0, 10) + " " + ele.slice(11, 19);

// 페이징 네비게이션 계산

// 목록 데이터 가져오는 함수
const fbGetList = () => {
  axios
    .get(import.meta.env.VITE_SERVER_MAIN_URL + "/comment", {
      headers: {
        "Content-Type": `application/json`,
        "ngrok-skip-browser-warning": "69420",
      },
    })
    .then((response) => {
      comments.value = response.data;
      applyFilters();
    })
    .catch((e) => console.log(e));
};

// 컴포넌트 마운트 시 데이터 가져오기
onMounted(() => {
  applyFilters();
  fbGetList();
});
</script>

<style scoped>
.filters {
  margin-bottom: 20px;
}

.pagination {
  margin-top: 20px;
}

th {
  text-align: center !important;
  border: 1px solid gainsboro !important;
}

td {
  border: 1px solid gainsboro !important;
}

tr > :first-child {
  text-align: right;
}

tr > :nth-child(6) {
  text-align: right;
}
</style>
