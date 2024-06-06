<template>
  <div class="flex flex-col justify-center items-center">
    <div class="grid grid-cols-5 sm:gap-4 md:gap-6 lg:gap-8 lg:w-[1000px] md:w-[800px] sm:w-[500px]justify-center mt-3">
      <span v-for="n in 5" :key="n" class="checkboxCard">
        <input type="radio" class="btn-check" name="radios" :id="printDays[n - 1].eng" v-model="day"
          :value="n.toString()" />
        <label class="btn-chk dayBtn content" :for="printDays[n - 1].eng">{{ printDates[n - 1] }}{{ printDays[n - 1].kor
          }}</label>
      </span>
    </div>
    <div class="flex flex-col">
      <div class="flex mt-10 justify-center">
        <div
          class="grid lg:grid-cols-5 md:grid-cols-5 sm:grid-cols-3 sm:gap-4 md:gap-6 lg:gap-8 lg:w-[1300px] md:w-[800px] sm:w-[500px]">
          <div class="lg:w-[200px] md:w-[150px] sm:w-[150px] mb-3" v-for="n in 5" :key="n">
            <div class="cardItem p-4 flex flex-col justify-center items-center text-center rounded-lg hover-scale gap-4"
              @click="writeMenu(n - 1)" :class="{ selected: n - 1 == menuType && reviewToggle }">
              <img :src="returnImgName2(n)" class="lg:w-3/5 md:w-6/10" alt="..." />
              <div class="p-2 h-[100px] flex flex-col justify-center items-center gap-5">
                <div class="flex flex-col justify-center items-center">
                  <h5 class="card-title" v-html="titles[n - 1]"></h5>
                  <p class="card-text overflow-y-auto scrollbar-hide" v-html="printMenu[n - 1]"
                    :style="{ display: !closed[n - 1] ? 'block' : 'none' }"></p>
                </div>
              </div>
              <button @click="soldOut(n - 1)" :class="{
                btn: true,
                'btn-danger': isSoldout[n - 1],
                'btn-outline-danger': !isSoldout[n - 1],
              }" :style="{ display: closed[n - 1] ? 'none' : 'block' }">
                {{ isSoldout[n - 1] ? "품절 취소" : "품절" }}
              </button>
            </div>
          </div>
        </div>
      </div>
      <div
        class="px-4 mt-10 justify-center items-center grid lg:grid-cols-2 md:grid-cols-2 sm:grid-cols-1 sm:gap-4 md:gap-6 lg:gap-8 lg:w-[1300px] md:w-[800px] sm:w-[500px]">
        <div class="mb-3 border border-gray p-3 rounded-md w-full lg:w-3/4">
          <h6 class="w-full">
            <div v-if="!day">
              <p>Loading...</p>
            </div>
            <div v-else>
              {{ printDates[day - 1] }}{{ printDays[day - 1].kor }}
              {{
                reviewToggle
                  ? titles[selectedMenu.type]
                  : `어떤 메뉴도 선택하지 않았습니다.`
              }}
            </div>
          </h6>

          <div class="flex flex-col justify-center items-center">
            <div class="rating">
              <label v-for="(rating, index) in ratings" :key="`star-${index}`"
                :class="['rating__label_new', rating.class]" @click="selectRating(rating.value)">
                <span :class="[{ filled: rating.value <= selectedRating }]">★★★★★</span>
                <input type="radio" :id="`star${rating.value}`" class="rating__input" name="rating" value="" />
              </label>
            </div>
            <ul class="flex gap-2 pl-0 items-center scoreText" style="font-size: small">
              <li :class="[{ scoreTextChk: selectedRating == 1 }]">
                아주 안좋음
              </li>
              <span class="v-line"></span>
              <li :class="[{ scoreTextChk: selectedRating == 2 }]">안좋음</li>
              <span class="v-line"></span>
              <li :class="[{ scoreTextChk: selectedRating == 3 }]">보통</li>
              <span class="v-line"></span>
              <li :class="[{ scoreTextChk: selectedRating == 4 }]">좋음</li>
              <span class="v-line"></span>
              <li :class="[{ scoreTextChk: selectedRating == 5 }]">
                아주 좋음
              </li>
            </ul>
          </div>

          <div class="my-3">
            <div class="flex flex-col gap-3 w-full">
              <div class="col-sm-10">
                <input v-model="fbContent" type="email" class="form-control" id="colFormLabel"
                  placeholder="오늘의 후기를 남겨주세요(선택)" style="width: 300px" />
              </div>
              <div class="col-auto flex justify-end">
                <button @click="fbSend" type="button" class="btn btn-primary"
                  :disabled="!reviewToggle || selectedRating == 0">
                  피드백 보내기
                </button>
              </div>
            </div>
          </div>
        </div>

        <TimeTable2 />
      </div>
    </div>
  </div>
</template>

<script setup>
import axios from "axios";
import { onMounted, ref, watch } from "vue";

import TimeTable2 from "./TimeTable0519.vue";

const fbContent = ref("");

const selectedRating = ref(0);

const selectRating = (rating) => {
  selectedRating.value = rating;
};

const fbSend = (e) => {
  const formData = new FormData();
  formData.append("userId", 123);
  formData.append("menu", selectedMenu.value.id);
  formData.append("content", fbContent.value);
  formData.append("rate", selectedRating.value);
  formData.append("date", new Date());

  axios
    .post(import.meta.env.VITE_SERVER_MAIN_URL + "/comment/write", formData, {
      headers: {
        "Content-Type": "multipart/form-data",
      },
    })
    .then((res) => {
      console.log(res);
    });
};

const oneWeek = ref([]);
const list = ref([]);
const dates = ref([]);
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
const day = ref(0);

const soldout = ref([]);
const soldoutvalue = new Set();
const isSoldout = ref([false, false, false, false, false]);
const closed = ref([false, false, false, false, false]);

const setMenus = (val) => {
  // 요일로 조회함
  axios
    .get(import.meta.env.VITE_SERVER_MAIN_URL + "/menu", {
      headers: {
        "ngrok-skip-browser-warning": "anykey",
      },
    })
    .then((response) => (list.value = response.data));

  isSoldout.value = [false, false, false, false, false];
  closed.value = [false, false, false, false, false];

  list.value.map((ele) => {
    const thisDate = new Date(ele.date);

    if (thisDate.getDay() == val) {
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
};

watch(day, (nl, ol) => setMenus(nl));

onMounted(() => {
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
      list.value.forEach((element) => datevalue.add(element.date));
      dates.value = [...datevalue];
      oneWeek.value = [...datevalue];
      dates.value.forEach((ele) => printDates.value.push(ele.substring(5, 10)));
      day.value = new Date().getDay();
      day.value = day.value == 6 || day.value == 0 ? 1 : day.value;
      setMenus(day.value);
    })
    .catch((error) => {
      console.error("Error fetching data:", error);
    });
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

const returnImgName2 = (n) => {
  return `/assets/img/image${n}.png`;
};

const mattermost = (type, content) => {
  const todaysMenu = ``;

  let message = type ? `### 앗! ${content} 품절 :sad_blob:` : todaysMenu;

  axios.post("https://meeting.ssafy.com/hooks/wp3hwhdq7tbb7qcpteydrd9dwa", {
    channel: "Menu",
    text: message,
    username: "밥 봇",
    icon_emoji: ":sad_blob:",
  });
};
</script>

<style scoped>
.rating_box {
  display: flex;
}

.ratingStar {
  position: relative;
  color: #ddd;
  font-size: 30px;
  text-align: center;
}

.ratingStar input {
  position: absolute;
  left: 0;
  right: 0;
  width: 100%;
  height: 100%;
  opacity: 0;
  cursor: pointer;
}

.filled {
  color: #ffc107;
}

.rating_star {
  width: 0;
  color: #ffc107;
  position: absolute;
  left: 0;
  right: 0;
  overflow: hidden;
  pointer-events: none;
}

.btn-chk {
  color: #4e5968;
}

.dayBtn {
  padding: 12px 10px;
  font-size: 15px;
  line-height: 20px;
  color: currentColor;
  border-radius: 8px;
  border: 0;
  cursor: pointer;
  background-color: transparent;
  text-decoration: none;
  text-align: left;
  border: 1px solid transparent;
}

.customCheckbox {
  position: relative;
  display: inline-block;
}

.customCheckbox::after {
  content: "";
  position: absolute;
  left: 0;
  bottom: -5px;
  height: 2px;
  width: 0;
  background-color: red;
  transition: width 0.3s ease;
}

.checkboxCard input:checked~label .content::after {
  width: 100%;
}

.content {
  --padding: 1.5rem;
  font-size: 15px;
  position: relative;
  cursor: pointer;
}

.content::after {
  content: "";
  position: absolute;
  left: 0;
  bottom: -0.1rem;
  height: 3px;
  width: calc(100%);
  background: rgb(49, 130, 246);
}

@media (hover) {
  .content:hover::after {
    transform: scaleX(1);
    margin-left: 0;
  }

  .content::after {
    transform: scaleX(0);
    margin-left: 50%;
    transform-origin: left;
    transition: transform 500ms ease, margin-left 0.5s ease;
  }
}

.btn-check:active+.btn-outline-success,
.btn-check:checked+.btn-outline-success,
.btn-outline-success.active,
.btn-outline-success.dropdown-toggle.show,
.btn-outline-success:active {
  border: 1px solid rgba(78, 89, 104, 0.2);
  border-radius: 10px;
}

.btn-outline-success {
  border: 1px solid rgba(78, 89, 104, 0.2);
  border-radius: 10px;
}

.cardItem {
  background-color: #fff;
  border-radius: 18px;
  box-shadow: 2px 4px 12px #00000014;
  box-sizing: border-box;
  display: flex;
  flex-direction: column;
  height: 24rem;
  overflow: hidden;
  padding: 30px;
  position: relative;
}

.hover-scale {
  opacity: 1;
  transform: scale(1);
  transition-duration: 0.5s;
}

.hover-scale:hover {
  box-shadow: 2px 4px 16px #00000029;
  transform: scale3d(1.01, 1.01, 1.01);
}

.btn-label {
  margin-left: 70px;
}

.selected {
  border: 3px solid rgba(49, 130, 246, 0.3);
}

.rating__input {
  display: none;
}

.rating__label .star-icon {
  width: 18px;
  height: 36px;
  display: block;
  background-image: url("../assets/images/star36.svg");
  background-repeat: no-repeat;
}

.rating__label_new {
  width: 30px;
  margin: 0 8px;
  overflow: hidden;
  cursor: pointer;
}

.v-line {
  border-left: 1px solid rgba(78, 89, 104, 0.8);
  height: 18px;
}

.scoreText {
  color: rgba(78, 89, 104, 0.4);
}

.scoreTextChk {
  color: rgb(49, 130, 246);
}

.rating__label {
  width: 18px;
  overflow: hidden;
  cursor: pointer;
}

.rating__label .star-icon {
  width: 18px;
  height: 36px;
  display: block;
  position: relative;
  left: 0;
  background-image: url("../assets/images/fillStar36.svg");
  background-repeat: no-repeat;
}

.rating__label .star-icon.filled {
  width: 18px;
  height: 36px;
  display: block;
  position: relative;
  left: 0;
  background-image: url("../assets/images/star36.svg");
  background-repeat: no-repeat;
}

.rating__label--full .star-icon {
  background-position: right;
}

.rating__label--half .star-icon {
  background-position: left;
}
</style>