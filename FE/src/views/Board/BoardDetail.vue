<template>
  <div class="board-detail">
    <div class="board-contents">
      <h5>메뉴판 기간</h5>
      <input type="text" class="form-control" id="exampleFormControlInput1" :value="boardTitle"
        :disabled="writerConfirm" />

      <!-- <DatePickerRange @handle-date-change="pickDates" /> -->

      <!-- 공백 사각형 추가 -->
      <div class="container" style="margin-top: 20px; margin-bottom: 20px" />
      <h5>20층 메뉴</h5>
      <div class="input-group" style="display: flex; flex-direction: column">
        <!-- 공백 사각형 추가 -->
        <div class="container" style="margin-top: 10px; margin-bottom: 20px" />

        <div class="w-full">
          <span>
            <img :src="fileSrc[1]" />
            <table>
              <tr>
                <!-- <th>날짜</th> -->
                <th>한식</th>
                <th>일품</th>
              </tr>
              <tr v-for="i in 5" :key="i">
                <!-- <td>{{ dates[i - 1] }}{{ days[new Date(dates[i - 1]).getDay()] }}</td> -->
                <td v-for="j in 2" :key="j">
                  <textarea rows="8" cols="25" @change="tempChange" v-model="content_20F[(i - 1) * 2 + j - 1]"
                    :disabled="writerConfirm"></textarea>
                </td>
              </tr>
            </table>
          </span>
        </div>
      </div>
      <!-- 공백 사각형 추가 -->
      <div class="container" style="margin-top: 30px; margin-bottom: 20px"></div>

      <!-- 공백 사각형 추가 -->
      <h5>10층 메뉴</h5>
      <div class="container" style="margin-top: 30px; margin-bottom: 20px" />
      <div class="w-full">
        <span>
          <img :src="fileSrc[0]" />
          <table border="1">
            <tr>
              <!-- <th>날짜</th> -->
              <th>도시락</th>
              <th>샌드위치&샐러드</th>
              <th>샐러드</th>
            </tr>
            <tr v-for="i in 5" :key="i">
              <!-- <td>{{ dates[i - 1] }}{{ days[new Date(dates[i - 1]).getDay()] }}</td> -->
              <td v-for="j in 3" :key="j">
                <textarea rows="8" cols="25" @change="tempChange" v-model="content_10F[(i - 1) * 3 + j - 1]"
                  :disabled="writerConfirm"></textarea>
              </td>
            </tr>
          </table>
        </span>
      </div>
      <div class="common-buttons">
        <button type="button" class="w3-button w3-round w3-gray" @click="goBoardList">
          목록</button>&nbsp;
        <button type="button" class="w3-button w3-round w3-blue-gray" @click="modifySubmit" :hidden="!writerConfirm">
          수정</button>&nbsp;
        <button type="button" class="w3-button w3-round w3-blue-gray" @click="submit" :hidden="writerConfirm">
          저장</button>&nbsp;
        <button type="button" class="w3-button w3-round w3-blue-gray" @click="deleteRequest">
          삭제</button>&nbsp;
      </div>
    </div>
  </div>
</template>

<script setup>
import DatePickerRange from "../../components/DatePickerRange.vue";
import { ref, onMounted } from "vue";
import { useRoute } from "vue-router";
import { useRouter } from "vue-router";
const router = useRouter();
const goBoardList = () => router.push(`/admin/board/list`);

const route = useRoute();
// const goBoardList = () => router.push('/admin/board/list');
const boardId = route.params.boardId;
const boardDetailContent = ref([]);
const boardTitle = ref("");
const boardWriterId = ref("");
const content_10F = ref([]);
const content_20F = ref([]);
// 가져온 데이터를 담을 배열
const call10f = ref([]);
const call20f = ref([]);

import axios from "axios";
const tempChange = (e) => {
  console.log(e.target.value);
};

const fval = ref(0); // 선택한 층수
const fileSrc = ref([]);
const result10 = ref([]);
const result20 = ref([]);
const dates = ref([]);
const days = ref(["(월)", "(화)", "(수)", "(목)", "(금)", "(토)", "(일)"]);

onMounted(() => {
  getBoardDetail();
});

/**
 * 게시글 상세 정보를 가져오는 함수
 */
const getBoardDetail = () => {
  axios
    .get(import.meta.env.VITE_SERVER_MAIN_URL + `/board/${boardId}`, {
      headers: {
        "Content-Type": "multipart/form-data",
        "ngrok-skip-browser-warning": "69420",
      },
    })
    .then((res) => {
      console.log("클릭했을 떄 !!!!!", res);
      boardDetailContent.value = res.data[1];
      boardTitle.value = res.data[0].startdate + " - " + res.data[0].enddate;
      boardWriterId.value = res.data[0].userId; //글쓴이Id를 저장하는 변수
      //boardWriterId를 찾아라 !!!!!
      console.log(boardDetailContent.value[1].type);
      divFloorFilters();
    });
};

/**
 * boardDetailContent를 순회하면서 10층 메뉴랑 20층 메뉴를 구분해주는 함수
 */
const divFloorFilters = () => {
  boardDetailContent.value.map((item) => {
    if (item.type === 0 || item.type === 1) {
      call20f.value.push(item);
    } else if (item.type === 2 || item.type === 3 || item.type === 4) {
      call10f.value.push(item);
    }
  });

  call20f.value.map((each) => {
    content_20F.value.push(each.content);
  });

  call10f.value.map((each) => {
    content_10F.value.push(each.content);
  });
  // console.log(content_10F.value)
  // console.log("20층 메뉴다!!!!!", content_20F.value[3].content)
};

const floor = (n) => {
  // 선택한 층수(파라메터 추가하지 않으면서 upload 함수 하나로 쓰려고)
  fval.value = n;
};

//보내는 요청인데 이거 리스트에서 사용해야함
//리스트에서 클릭시 요청 보내고
// 처음에 리스트 화면 접근 시 요청 보내고(리스트 달라고)
const upload = (e) => {
  const formData = new FormData();
  const file = e.target.files[0]; // 선택된 파일 가져오기
  if (fval.value == 20) {
    fileSrc.value[1] = import.meta.env.VITE_SERVER_MAIN_URL + "/assets/";
    fileSrc.value[1] += file.name;
  } else if (fval.value == 10) {
    fileSrc.value[0] = import.meta.env.VITE_SERVER_MAIN_URL + "/assets/";
    fileSrc.value[0] += file.name;
  }
  formData.append("file", file); // FormData에 파일 추가

  axios
    .post(
      import.meta.env.VITE_SERVER_MAIN_URL + "/assets/upload/" + fval.value,
      formData,
      {
        headers: {
          "Content-Type": "multipart/form-data",
        },
      }
    )
    .then((res) => {
      fval.value == 20
        ? (result20.value = res.data.images[0].fields)
        : (result10.value = res.data.images[0].fields);
    });
};

const writerConfirm = ref(true);
/**
 * 수정을 눌렀을 때 작성자가 맞는지 확인해주는 함수
 */
const modifySubmit = () => {
  if (localStorage.getItem("id") === boardWriterId.value) {
    writerConfirm.value = false;
  } else {
    alert("게시글의 수정 권한이 없습니다.");
  }
};

/**
 * 저장을 눌렀을 때 수정한 폼을 수정요청하는 함수
 */
const submit = () => {
  console.log(result10.value);
  console.log(result20.value);
  alert("저장 되었습니다.");
  goBoardList();
};

/**
 * 수정을 눌렀을 때 작성자가 맞는지 확인해주는 함수
 */
const deleteRequest = () => {
  if (localStorage.getItem("id") === boardWriterId.value) {
    if (confirm("삭제 하시겠습니까?")) {
      //삭제 요청 보내는 axios 보내기

      alert("정상적으로 삭제되었습니다.");
    }
  } else {
    alert("게시글의 삭제 권한이 없습니다.");
  }
};

//받는 요청
const boardGetList = () => {
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

const startDate = ref();
const endDate = ref();

const pickDates = (...vars) => {
  const val = vars[0];
  startDate.value = new Date(val.substring(0, 10));
  endDate.value = new Date(val.substring(11));

  const diff = (endDate.value - startDate.value) / (1000 * 60 * 60) / 24;
  if (diff != 4) {
    endDate.value.setDate(startDate.value.getDate() + 4);
    alert("시작일 기준으로 5일 선택되었습니다.");
  }
  // 달력에도 동기화로 어떻게 뿌리지 ?
  dates.value = []; // 날짜 지정할 때마다 초기화하기
  for (let i = 0; i < 5; i++) {
    let thisDate = new Date().setDate(startDate.value.getDate() + i);
    thisDate = new Date(thisDate);
    //let str = thisDate.getMonth()+1 < 10 ? thisDate.getMonth()+1 : '0'+toString(thisDate.getMonth()+1)
    let str = thisDate.getMonth() + 1;
    str = str < 10 ? "0" + str : str;
    str += "-";
    str +=
      thisDate.getDate() < 10 ? "0" + thisDate.getDate() : thisDate.getDate();
    dates.value.push(str);
    // 시작일로부터 5일 배열에 넣기
  }

  console.log(dates.value);
};

const getDatePickerTitle = (elem) => {
  // From the label or the aria-label
  const label = elem.nextElementSibling;
  let titleText = "";
  if (label && label.tagName === "LABEL") {
    titleText = label.textContent;
  } else {
    titleText = elem.getAttribute("aria-label") || "";
  }
  return titleText;
};

const elems = document.querySelectorAll(".datepicker_input");
for (const elem of elems) {
  const datepicker = new Datepicker(elem, {
    format: "dd/mm/yyyy", // UK format
    title: getDatePickerTitle(elem),
  });
}
</script>

<style scoped>
body {
  /* Centring content */
  min-height: 100vh;
  display: grid;
  place-content: center;
}

/* Adapts a floating label for use with a left-handed input group icon */
.form-floating>.bi-calendar-date+.datepicker_input+label {
  padding-left: 3.5rem;
  z-index: 3;
}

th {
  text-align: center;
}
</style>
