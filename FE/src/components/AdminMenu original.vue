<template>
  <div>
    <div>
      <h2>메뉴</h2>
      <span v-for="n in 5">
        <input type="radio" class="btn-check" name="radios" :id="printDays[n - 1].eng" v-model="day"
          :value="n.toString()" />
        <label class="btn btn-outline-success btn-label" :for="printDays[n - 1].eng">{{ printDates[n - 1] }}{{
          printDays[n - 1].kor }}</label>
      </span>
    </div>
    <!--  default value 추가 -->
    <div class="container" style="margin-top: 60px; margin-bottom: 20px"></div>

    <!-- 카드 요소들 -->
    <div class="container">
      <div class="row menu1">
        <div class="col-md-4 mb-3" v-for="n in 2">
          <div class="card">
            <img :src="returnImgName(n, 5)" class="card-img-top" alt="lala" />
            <div class="card-body">
              <h5 class="card-title">{{ titles[n - 1] }}</h5>
              <!-- <p class="card-text">{{ printMenu[n-1] }}</p> -->
              <p class="card-text" v-html="printMenu[n - 1]" :style="{ display: !closed[n - 1] ? 'block' : 'none' }">
              </p>
              <button @click="soldOut(n - 1)" :class="{
                btn: true,
                'btn-danger': isSoldout[n - 1],
                'btn-outline-danger': !isSoldout[n - 1],
              }" :style="{ display: closed[n - 1] ? 'none' : 'block' }">
                {{ isSoldout[n - 1] ? "품절 취소" : "품절" }}
              </button>
              <!-- 미운영일 때 disabled된 버튼 하나 추가 -->
              <span class="btn btn-outline-secondary" :style="{ display: !closed[n - 1] ? 'none' : 'block' }">미운영</span>
            </div>
          </div>
        </div>
      </div>

      <div class="row menu2">
        <div class="col-md-4 mb-3" v-for="n in 3">
          <div class="card">
            <img :src="returnImgName(n, 7)" class="card-img-top" alt="..." />
            <div class="card-body">
              <h5 class="card-title">{{ titles[n + 1] }}</h5>
              <p class="card-text" v-html="printMenu[n + 1]" :style="{ display: !closed[n + 1] ? 'block' : 'none' }">
              </p>
              <button @click="soldOut(n + 1)" :class="{
                btn: true,
                'btn-danger': isSoldout[n + 1],
                'btn-outline-danger': !isSoldout[n + 1],
              }" :style="{ display: closed[n + 1] ? 'none' : 'block' }">
                {{ isSoldout[n + 1] ? "품절 취소" : "품절" }}
              </button>
              <span class="btn btn-outline-secondary" :style="{ display: !closed[n + 1] ? 'none' : 'block' }">미운영</span>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
/*
      ============== TODOS
      -> mattermost hook 추가하기  (fin) 
      -> 리팩토링 흐항항 (String 말고 객체 뿌리는 방식으로 했음 좋겠지만 안될 것 같음 ㅋㅋ)
      -> user화면 pulling (여기말구 UserMenu.vue) 
      -> 품절 누를 때 '이 메뉴를 품절로 공지할까요?' 모달 띄우기 (선택)

*/

import axios from "axios";
import { onMounted, ref, watch } from "vue";
const list = ref([]); // 메뉴를 넣을 배열
const comments = ref([]); //코멘트를 넣을 배열
const dates = ref([]); // 중복 없는 날짜 모음

const printDates = ref([]);
const printDays = ref([
  { kor: "(월)", eng: "mon" },
  { kor: "(화)", eng: "tue" },
  { kor: "(수)", eng: "wed" },
  { kor: "(목)", eng: "thu" },
  { kor: "(금)", eng: "fri" },
]);
const titles = ref([
  "20F 한식",
  "20F 일품",
  "10F 도시락",
  "10F 샌드위치 & 샐러드",
  "10F 샐러드",
]);
const printMenu = ref([]);
const day = ref(0); // 숫자 형태

const soldout = ref([]);
const soldoutvalue = new Set(); // 중복이 없어야 해서 ..
const isSoldout = ref([false, false, false, false, false]); // 품절 여부
const closed = ref([false, false, false, false, false]); // 미운영

const setMenus = (val) => {
  // 요일로 조회함
  axios
    .get(import.meta.env.VITE_SERVER_MAIN_URL + "/menu", {
      headers: {
        "ngrok-skip-browser-warning": "anykey",
      },
    })
    .then((response) => (list.value = response.data)); // 요일을 바꿀 때마다 서버로 가서 새로 이번 주 메뉴의 정보값을 가져오도록 함

  isSoldout.value = [false, false, false, false, false];
  closed.value = [false, false, false, false, false]; // 초기화

  list.value.map((ele) => {
    // filter 써도 됨
    const thisDate = new Date(ele.date);

    if (thisDate.getDay() == val) {
      // 조회할 요일과 같은 요일의 데이터만 고르기
      printMenu.value[ele.type] = ele.content;
      switch (ele.status) {
        case 0:
          break;
        case 1: {
          isSoldout.value[ele.type] = true;
          break;
        }
        case 2: {
          closed.value[ele.type] = true;
          break;
        }
      }
    }
  });

  console.log(isSoldout.value);
  console.log(closed.value);
};

watch(day, (nl, ol) => setMenus(nl)); // 조회 날짜 바꿀 때(day 바뀔 때)

onMounted(() => {
  // 맨 처음에 페이지가 생길 때
  axios
    .get(import.meta.env.VITE_SERVER_MAIN_URL + "/menu", {
      headers: {
        "Content-Type": `application/json`,
        "ngrok-skip-browser-warning": "69420",
      },
    })
    .then((response) => {
      list.value = response.data;
      const datevalue = new Set();

      list.value.map((element) => {
        datevalue.add(element.date);
      }); // 날짜 세팅하기
      dates.value = [...datevalue];
      dates.value.map((ele) => printDates.value.push(ele.substring(5, 10)));
      // 데이터를 변수에 할당하는 작업 수행
      day.value = new Date().getDay(); // 현재 요일로 초기화
      day.value = day.value == 6 || day.value == 0 ? 1 : day.value; // 토요일이거나 일요일이면 다음 주 식단, 월요일로 세팅합니다.
      setMenus(day.value); // 오늘 날짜 메뉴 띄우기
      console.log(list.value);
    })
    .catch((error) => {
      console.error("Error fetching data:", error);
    });
  // ========================================================================================================================================
  axios
    .get(import.meta.env.VITE_SERVER_MAIN_URL + "/comment", {
      headers: {
        "Content-Type": `application/json`,
        "ngrok-skip-browser-warning": "69420",
      },
    })
    .then((response) => {
      comments.value = response.data;
      console.log("response ====>", response);
      // console.log("comment ===>", comment);
    })
    .catch((e) => console.log(e));
  //    getSoldOut()
});

const soldOut = (index) => {
  if (confirm("이 메뉴를 품절로 변경할까요?")) {
    isSoldout.value[index] = !isSoldout.value[index];
    const status = isSoldout.value[index] ? 1 : 0;
    if (isSoldout.value[index]) mattermost(status, titles.value[index]);
    index += 5 * (day.value - 1);
    console.log(list.value[index]);
    console.log(isSoldout.value);
    axios
      .put(
        import.meta.env.VITE_SERVER_MAIN_URL +
        "/menu/soldout/" +
        list.value[index].id +
        "/" +
        status
      )
      .catch((e) => {
        console.log(soldout.value);
        console.log(e);
      });
  }
  else {

  }
  //    getSoldOut()
};


const returnImgName = (n, add) => {
  // 이미지 이름 임시부여
  let N = n + add;
  return "/image (" + N + ").png";
};

// MATTERMOST =============================
// type : true / false (true : 메뉴 품절 안내 / false : 오늘의 메뉴 정시알림 )

const mattermost = (type, content) => {
  // type ? content == titles[index] 들어감 : 내용 없음
  const todaysMenu = ``; // 이거는 백에서 처리할 거라 T^ 일단 놔두긴 했는데 쓸 일은 없을 것

  let message = type ? `### 앗! ${content} 품절 :sad_blob:` : todaysMenu;

  axios.post("https://meeting.ssafy.com/hooks/wkzwo7rf1p87mbygh15fsebjmy", {
    channel: "botTest",
    text: message,
    username: "밥 봇", // username / icon 못 바꾸게 설정해둔 것 같다 .. ㅠㅎ
    icon_emoji: ":sad_blob:",
  });
};
</script>

<style scoped>
.btn-label {
  margin-left: 70px;
  /* 원하는 만큼의 여백을 지정합니다. */
}

.menu1 {
  justify-content: space-evenly;
  /* 첫 번째 줄 카드 요소들을 균등하게 배치합니다. */
}

.menu2 {
  justify-content: space-around;
  /* 두 번째 줄 카드 요소들을 균등하게 배치합니다. */
}
</style>
