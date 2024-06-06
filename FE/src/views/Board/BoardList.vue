<template>

  <div class="board_wrap">
    <div class="board_title">
      <strong>메뉴판 업로드 내역</strong>
      <p>회원님이 업로드하신 메뉴 업로드 내역을 보여드립니다.</p>
    </div>

    <div class="filters flex gap-2">
      <input
        class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
        type="text" placeholder="제목(시작날짜 - 종료날짜)" v-model="searchTitle" />
      <input
        class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
        type="text" placeholder="작성자" v-model="searchWriter" />
      <input
        class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
        type="text" placeholder="작성일시" v-model="searchregDate" />


      <button
        class="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm w-full sm:w-auto px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"
        @click="applyFilters">
        검색
      </button>
    </div>
    <div class="board_list_wrap">
      <div class="board_list">
        <div class="top">
          <div class="num">No</div>
          <div class="feedbackContent">제목(시작날짜 - 종료날짜)</div>
          <div class="menuName">작성자</div>
          <div class="feedbackContent">작성일시</div>
        </div>
        <div @click="goBoardDetail(comment.id)" v-for="(comment, index) in paginatedComments" :key="comment.id">
          <div class="num"> {{ index + 1 + 10 * (currentPage - 1) }}</div>
          <div class="feedbackContent">{{ comment.startdate }} - {{ comment.enddate }}</div>
          <div class="menuName">{{ comment.userId }}</div>
          <div class="feedbackContent">{{ splitHour(comment.date) }}</div>
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
import { useRouter } from "vue-router";
import axios from "axios";
const router = useRouter();
const goBoardDetail = (e) => router.push(`/admin/board/detail/${e}`);
const menuBoard = ref([]); // 게시글 리스트를 넣을 배열

const searchTitle = ref("");
const searchWriter = ref("");
const searchregDate = ref("");

const currentPage = ref(1);
const commentsPerPage = 10;
const splitHour = (ele) => ele.slice(0, 10) + " " + ele.slice(11, 19);

const filteredComments = ref([]);

const goMain = () => router.push('/');

const applyFilters = () => {
  filteredComments.value = menuBoard.value
    .filter((comment) => {
      return (
        (searchTitle.value === "" ||
          comment.startdate.includes(searchTitle.value) ||
          comment.enddate.includes(searchTitle.value)) &&
        (searchWriter.value === "" ||
          comment.userId.includes(searchWriter.value)) &&
        (searchregDate.value === "" ||
          comment.date.includes(searchregDate.value))
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

// 페이징 네비게이션 계산

// 목록 데이터 가져오는 함수 (메뉴판 기간)
const menuGetList = () => {
  axios
    .get(import.meta.env.VITE_SERVER_MAIN_URL + "/board", {
      headers: {
        "Content-Type": `application/json`,
        "ngrok-skip-browser-warning": "69420",
      },
    })
    .then((response) => {
      console.log("response는", response.data);
      menuBoard.value = response.data;
      console.log("menuBoard.value는 !!!!", menuBoard.value);
      applyFilters();
    })
    .catch((e) => console.log(e));
};

// 컴포넌트 마운트 시 데이터 가져오기
onMounted(() => {
  applyFilters();
  menuGetList();
  // console.log(menuBoard._value)
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
  margin: 100px auto;
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
  width: 100px;
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
  width: 300px;
  /* ㄴ🟡번호칸이 10%의 영역을 가지게 된다. */
}

.board_list .menuName {
  width: 250px;
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
  text-align: center;
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