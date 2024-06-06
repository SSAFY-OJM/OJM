<template>
  <div class="board_wrap">
    <div class="board_title">
      <strong>내가 쓴 후기</strong>
      <p>회원님이 작성하신 메뉴 후기를 보여드립니다.</p>
    </div>

    <div class="filters flex gap-2">
      <input
        class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
        type="text" placeholder="제공 일시" v-model="searchMenuDate" />
      <input
        class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
        type="text" placeholder="메뉴 종류" v-model="searchMenuType" />
      <input
        class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
        type="text" placeholder="메뉴명" v-model="searchMenu" />
      <input
        class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
        type="text" placeholder="후기 내용" v-model="searchContent" />

      <input
        class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
        type="text" placeholder="등록 일시" v-model="searchWriteDate" />

      <button
        class="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm w-full sm:w-auto px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"
        @click="applyFilters">
        검색
      </button>
    </div>
    <div class="alert alert-primary" role="alert">
      평균별점은 {{ averageRateNumber }}점 입니다.
    </div>
    <div class="board_list_wrap">
      <div class="board_list">
        <div class="top">
          <div class="num">No</div>
          <div class="serveDate">제공 일시</div>
          <div class="menuType">메뉴 종류</div>
          <div class="menuName">메뉴명</div>
          <div class="feedbackContent">후기 내용</div>
          <div class="rating">별점</div>
          <div class="submitDate">등록일시</div>
        </div>
        <div v-for="(comment, index) in paginatedComments" :key="comment.id">
          <div class="num">{{ index + 1 + 10 * (currentPage - 1) }}</div>
          <div class="serveDate">{{ splitDate(comment.menuDate) }}</div>
          <div class="menuType">{{ titles[comment.type] }}</div>
          <div class="menuName">
            {{ getFirstLine(replaceBr(comment.menu)) }}
          </div>
          <div class="feedbackContent">{{ comment.content }}</div>
          <div class="rating">{{ convertToStars(comment.rate) }}</div>
          <div class="submitDate">{{ splitHour(comment.commentDate) }}</div>
        </div>
      </div>
      <div class="pagination">
        <button @click="changePage(currentPage - 1)" :disabled="currentPage <= 1">
          이전
        </button>
        <span>페이지 {{ currentPage }} / {{ totalPages }}</span>
        <button @click="changePage(currentPage + 1)" :disabled="currentPage >= totalPages">
          다음
        </button>
      </div>
      <div class="bt_wrap">
        <a @click="goMain()" class="on">목록</a>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from "vue";
import { useRoute } from "vue-router";
import { useRouter } from "vue-router";
import axios from "axios";

const router = useRouter();
const userId = localStorage.getItem("id");
const goMain = () => router.push(`/`);

const route = useRoute(); // 라우트 쿼리 파라미터 가져오기
const comments = ref([]); // 피드백 리스트를 넣을 배열

const replaceBr = (e) => {
  return e.replace(/\\r\\n|\\n|\\r/gm, "<br>");
};

const getFirstLine = (e) => {
  return e.split("<br>")[0].slice(0, 15);
};

const convertToStars = (rate) => {
  return "⭐".repeat(rate) + "✰".repeat(5 - rate);
};

const calculateAverageRate = (e) => {
  let totalRate = 0;

  // 모든 객체의 rate 값을 합산
  for (let i = 0; i < e.length; i++) {
    totalRate += e[i].rate;
  }

  // 배열의 길이만큼 나눠서 평균을 계산
  let averageRate = totalRate / e.length;

  return parseInt(averageRate);
};

const titles = [
  "20F 한식",
  "20F 일품",
  "10F 도시락",
  "10F 샌드위치 & 샐러드",
  "10F 샐러드",
];

const searchMenuDate = ref("");
const searchMenuType = ref("");
const searchWriteDate = ref("");
const searchMenu = ref("");
const searchContent = ref("");
const currentPage = ref(1);
const commentsPerPage = 10;

const filteredComments = ref([]);
let averageRateNumber = ref(0);

const applyFilters = () => {
  filteredComments.value = comments.value
    .filter((comment) => {
      return (
        (searchMenuDate.value === "" ||
          comment.menuDate.includes(searchMenuDate.value)) &&
        (searchMenuType.value === "" ||
          titles[comment.type].includes(searchMenuType.value)) &&
        (searchMenu.value === "" || comment.menu.includes(searchMenu.value)) &&
        (searchContent.value === "" ||
          comment.content.includes(searchContent.value)) &&
        (searchWriteDate.value === "" ||
          comment.commentDate.includes(searchWriteDate.value))
      );
    })
    .reverse();
  currentPage.value = 1; // 필터를 새로 적용하면 페이지를 1로 초기화
  averageRateNumber.value = calculateAverageRate(filteredComments.value);
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

const splitHour = (ele) => ele.slice(0, 10) + " " + ele.slice(11, 16);

// 페이징 네비게이션 계산

// 목록 데이터 가져오는 함수
const fbGetList = () => {
  axios
    .get(import.meta.env.VITE_SERVER_MAIN_URL + `/comment/${userId}`, {
      headers: {
        "Content-Type": `application/json`,
        "ngrok-skip-browser-warning": "69420",
      },
    })
    .then((response) => {
      comments.value = response.data;
      applyFilters();
      console.log(response);
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

tr> :first-child {
  text-align: right;
}

tr> :nth-child(6) {
  text-align: right;
}

/* 여기서부터 수정본 CSS */

* {
  margin: 0;
  padding: 0;
}

html {
  font-size: 10px;
}

ul,
li {
  list-style: none;
}

a {
  text-decoration: none;
  color: inherit;
  /* ㄴ🟡 컬러 상속받아라 */
}

.board_wrap {
  width: 1000px;
  margin: 80px auto;
}

.board_title {
  margin-bottom: 20px;
}

.board_title strong {
  font-size: 3rem;
}

.board_title p {
  margin-top: 5px;
  font-size: 1rem;
}

.bt_wrap {
  margin-top: 30px;
  text-align: center;
  font-size: 0;
  text-align: center;
}

.bt_wrap a {
  display: inline-block;
  min-width: 80px;
  margin-left: 10px;
  padding: 10px;
  border: 1px solid #000;
  border-radius: 2px;
  font-size: 1rem;
}

.bt_wrap a:first-child {
  margin-left: 0;
}

.bt_wrap a.on {
  background: #000;
  color: #fff;
}

.board_list {
  width: 100%;
  border-top: 2px solid #000;
}

.board_list>div {
  border-bottom: 1px solid #ddd;
  font-size: 0;
}

.board_list>div.top {
  border-bottom: 1px solid #999;
}

.board_list>div:last-child {
  border-bottom: 1px solid #000;
}

.board_list>div>div {
  display: inline-block;
  /* ㄴ 🟡가로배치!!! */
  padding: 15px 0;
  /* ㄴ 🟡모든 픽셀 여백 15px 0 */
  text-align: center;
  font-size: 1.1rem;
  /* ㄴ 🟡부모에다가는 font-size : 0을 줌. */
}

.board_list>div.top>div {
  font-weight: 600;
}

.board_list .num {
  width: 40px;
}

.board_list .serveDate {
  width: 120px;
}

.board_list .menuType {
  width: 180px;
}

.board_list .menuName {
  width: 200px;
}

.board_list .feedbackContent {
  width: 220px;
  /* ㄴ🟡번호칸이 10%의 영역을 가지게 된다. */
}

.board_list .menuName {
  width: 170px;
  /* ㄴ🟡번호칸이 10%의 영역을 가지게 된다. */
}

.board_list .rating {
  width: 120px;
  /* ㄴ🟡번호칸이 10%의 영역을 가지게 된다. */
}

.board_list .submitDate {
  width: 120px;
  /* ㄴ🟡번호칸이 10%의 영역을 가지게 된다. */
}

.board_list .title {
  width: 10%;
  text-align: left;
}

.board_list .top .title {
  text-align: center;
}

.board_list .writer {
  width: 10%;
}

.board_list .date {
  width: 10%;
}

.board list .count {
  width: 10%;
}

.board_page {
  margin-top: 30px;
  /* ㄴ🟡 위로 여백 */
  text-align: center;
  /* ㄴ🟡 가운데정렬로 */
  font-size: 0;
  /* ㄴ🟡 페이지 순서 박스 사이의 여백 없애기 */
}

.board_page a {
  display: inline-block;
  width: 32px;
  height: 32px;
  box-sizing: border-box;
  /* ㄴ🟡 board_page a.bt과 num 에 패딩값을 넣게 되면서 상자가 길어지게 되는데, 이를 방지 하기 위해서 이 패딩값을 넘지 말아라~ 하는 코딩부분(전체높이 32px를 넘지 않게)*/
  vertical-align: middle;
  /* ㄴ🟡박스 높낮이 같게 */
  border: 1px solid #ddd;
  border-left: 0;
  line-height: 100%;
}

.board_page a.bt {
  padding-top: 10px;
  font-size: 1rem;
  letter-spacing: -1px;
}

.board_page a.num {
  padding-top: 9px;
  font-size: 1rem;
}

.board_page a.num.on {
  border-color: #000;
  background: #000;
  color: #fff;
}

.board_page a:first-child {
  border-left: 1px solid #ddd;
}
</style>
