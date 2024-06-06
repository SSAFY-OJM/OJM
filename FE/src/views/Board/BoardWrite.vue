<template>
  <div class="board-detail">
    <div class="board-contents">
      <h5>메뉴판 기간 설정</h5>
      <DatePickerRange @handle-date-change="pickDates" />
      <div class="container" style="margin-top: 20px; margin-bottom: 20px"></div>
      <h5>20층 메뉴판 업로드</h5>
      <div class="input-group">
        <input type="file" class="form-control" id="inputGroupFile04" aria-describedby="inputGroupFileAddon04"
          aria-label="Upload" @change="upload" @click="floor(20)" name="file" />
      </div>
      <div class="input-group" style="display: flex; flex-direction: column">
        <!-- 공백 사각형 추가 -->
        <div class="container" style="margin-top: 10px; margin-bottom: 20px"></div>

        <div class="w-full">
          <!-- <span class="input-group-text">수정 텍스트</span>
          <textarea
            class="form-control h-56"
            aria-label="With textarea"
          ></textarea> -->
          <span>
            <img :src="fileSrc[1]" />
            <table>
              <tr>
                <th>날짜</th>
                <th>한식</th>
                <th>일품</th>
              </tr>
              <tr v-for="i in 5" :key="i">
                <td>
                  {{ dates[i - 1] }}{{ days[new Date(dates[i - 1]).getDay()] }}
                </td>
                <td v-for="j in 2" :key="j">
                  <!-- <textarea rows="8" cols="25" @change="tempChange"
                    :value="result20.length > (i - 1) * 2 + j - 1 ? result20[(i - 1) * 2 + j - 1].inferText.length > 5 ? result20[(i - 1) * 2 + j - 1].inferText : '' : ''"></textarea> -->
                  <textarea rows="8" cols="25" v-model="model20[(i - 1) * 2 + j - 1]"></textarea>
                  <!-- {{(i-1)*2+j-1}} -->
                </td>
              </tr>
            </table>
          </span>
        </div>
      </div>
      <!-- 공백 사각형 추가 -->
      <div class="container" style="margin-top: 30px; margin-bottom: 20px"></div>

      <h5>10층 메뉴판 업로드</h5>
      <div class="input-group">
        <input type="file" class="form-control" id="inputGroupFile04" aria-describedby="inputGroupFileAddon04"
          aria-label="Upload" @change="upload" @click="floor(10)" name="file" />
      </div>
      <!-- 공백 사각형 추가 -->
      <div class="container" style="margin-top: 30px; margin-bottom: 20px"></div>
      <div class="w-full">
        <!-- <span class="input-group-text">수정 텍스트</span>
        <textarea
          class="form-control h-56"
          aria-label="With textarea"
        ></textarea> -->
        <span>
          <img :src="fileSrc[0]" />
          <table border="1">
            <tr>
              <th>날짜</th>
              <th>도시락</th>
              <th>샌드위치&샐러드</th>
              <th>샐러드</th>
            </tr>
            <tr v-for="i in 5" :key="i">
              <td>
                {{ dates[i - 1] }}{{ days[new Date(dates[i - 1]).getDay()] }}
              </td>
              <td v-for="j in 3" :key="j">
                <!-- <textarea rows="8" cols="25"
                  :value="result10.length > (i - 1) * 3 + j - 1 ? result10[(i - 1) * 3 + j - 1].inferText.length > 5 ? result10[(i - 1) * 3 + j - 1].inferText : '' : ''"></textarea> -->
                <textarea rows="8" cols="25" v-model="model10[(i - 1) * 3 + j - 1]"></textarea>
                <!-- {{(i-1)*3+j-1}} -->
              </td>
            </tr>
          </table>
        </span>
      </div>
      <div class="common-buttons">
        <button type="button" class="w3-button w3-round w3-blue-gray" @click="submit">
          저장</button>&nbsp;
        <button type="button" class="w3-button w3-round w3-gray" v-on:click="fnList" @click="goBoardList">
          목록
        </button>
      </div>
    </div>
  </div>
</template>

<script setup>
import DatePickerRange from "../../components/DatePickerRange.vue";
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import axios from "axios";
const router = useRouter();
const goBoardList = () => router.push(`/admin/board/list`);

const fval = ref(0); // 선택한 층수
const fileSrc = ref([]);
const result10 = ref([]);
const result20 = ref([]);
const model10 = ref([]);
const model20 = ref([]);
const send10 = ref([]);
const send20 = ref([]);
const sendBoard = ref({});
const sendMenu = ref([]);
const dates = ref([]);
const days = ref(["(월)", "(화)", "(수)", "(목)", "(금)", "(토)", "(일)"]);

const floor = (n) => {
  // 선택한 층수(파라메터 추가하지 않으면서 upload 함수 하나로 쓰려고)
  fval.value = n;
};

const upload = (e) => {
  const formData = new FormData();
  const file = e.target.files[0]; // 선택된 파일 가져오기

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
      if (fval.value == 20) {
        // fileSrc.value[1] = import.meta.env.VITE_SERVER_MAIN_URL + "/assets/";
        fileSrc.value[1] = "//70.12.103.162:8080" + "/assets/";
        console.log(file.name)
        fileSrc.value[1] += file.name;
        console.log(fileSrc.value[1])

      } else if (fval.value == 10) {
        // fileSrc.value[0] = import.meta.env.VITE_SERVER_MAIN_URL + "/assets/";
        fileSrc.value[0] = "//70.12.103.162:8080" + "/assets/";
        console.log(file.name)

        fileSrc.value[0] += file.name;
        console.log(fileSrc.value[0])
      }
      fval.value == 20
        ? (result20.value = res.data.images[0].fields)
        : (result10.value = res.data.images[0].fields);
      if (fval.value == 20) {
        result20.value.map((each) => {
          model20.value.push(each.inferText);
        });
      } else {
        result10.value.map((each) => {
          model10.value.push(each.inferText);
        });
      }


    });
};

const submit = () => {
  sendBoard.value = {
    userId: "admin", //localStorage.userId
    startdate: startDate.value,
    enddate: endDate.value, // DB로 가져갈 값
    date: new Date(),
    id: 0,
  };

  for (let i = 0; i < 10; i++) {
    let date = new Date(dates.value[Math.floor(i / 2)]);
    date.setFullYear(new Date().getFullYear());
    date = new Date(date);

    send20.value[i] = {
      type: i % 2,
      date,
      content: model20.value[i], // 데이터로 들어갈 필수 값
      id: 0,
      status: 0,
    };
  }

  for (let i = 0; i < 15; i++) {
    let date = new Date(dates.value[Math.floor(i / 3)]);
    date.setFullYear(new Date().getFullYear());
    date = new Date(date);

    send10.value[i] = {
      type: (i % 3) + 2,
      date,
      content: model10.value[i],
      id: 0,
      status: 0,
    };
  }
  sendMenu.value = [];

  send20.value.map((each) => sendMenu.value.push(each));
  send10.value.map((each) => sendMenu.value.push(each));

  axios
    .post(
      import.meta.env.VITE_SERVER_MAIN_URL + "/menu/add",
      JSON.stringify(sendMenu.value),
      {
        headers: {
          "Content-Type": "application/json",
        },
      }
    )
    .then((res) => console.log(res))
    .catch((e) => console.log(e));

  axios
    .post(
      import.meta.env.VITE_SERVER_M었IN_URL + "/board/write",
      JSON.stringify(sendBoard.value),
      {
        headers: {
          "Content-Type": "application/json",
        },
      }
    )
    .then((res) => console.log(res))
    .catch((e) => console.log(e));
  alert("정상적으로 저장되었습니다.");
  goBoardList();
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
