<template>
  <div class="w-full p-10 justify-center items-center">
    <form class="flex justify-center items-center">
      <table>
        <tr>
          <td>
            <h2>회원가입</h2>
          </td>
        </tr>
        <tr>
          <td>회원/관리자</td>
        </tr>
        <tr>
          <td>
            <select v-model="type">
              <option value="0">회원</option>
              <option value="1">관리자</option>
            </select>
          </td>
        </tr>
        <tr>
          <td>이름</td>
        </tr>
        <tr>
          <td><input type="text" class="text" v-model.trim="name" /></td>
        </tr>
        <tr>
          <td>아이디</td>
        </tr>
        <tr>
          <td><input type="text" class="text" v-model.trim="id" /></td>
          <td>
            <button type="button" class="btn" @click="checkId" style="width: 120px">
              아이디체크
            </button>
          </td>
        </tr>
        <tr>
          <td>비밀번호</td>
        </tr>
        <tr>
          <td>
            <input type="password" class="text" v-model.trim="password" required />
          </td>
        </tr>
        <tr>
          <td>캠퍼스</td>
        </tr>
        <tr>
          <td>
            <select v-model="campus" style="width: 140px">
              <option value="서울">서울캠퍼스</option>
              <option value="구미">구미캠퍼스</option>
              <option value="대전">대전캠퍼스</option>
              <option value="광주">광주캠퍼스</option>
              <option value="부울경">부울경캠퍼스</option>
            </select>
          </td>
        </tr>
        <tr :hidden="type == '0'">
          <td>관리자 코드</td>
        </tr>
        <tr :hidden="type == '0'">
          <td><input type="password" class="text" v-model.trim="adcode" /></td>
        </tr>

        <tr>
          <td :hidden="type == '1'" class="th">
            <div>
              <label>기수</label>
              <select v-model="nth" style="width: 75px">
                <option value="10">10기</option>
                <option value="11">11기</option>
              </select>
            </div>
            <div>
              <label>반</label>
              <input type="text" class="text2 p-2" v-model.trim="dept" style="width: 70px" required />
            </div>
            <div>
              <label>층</label>
              <input type="text" class="text2" v-model.trim="floor" style="width: 70px" required />
            </div>
          </td>
        </tr>
        <tr>
          <td>
            <button type="button" class="btn" @click="joinSend" :disabled="!idCheckToggle" style="width: 120px">
              가입하기
            </button>
          </td>
        </tr>
      </table>
    </form>
  </div>
</template>

<script setup>
import axios from "axios";
import { ref } from "vue";
import { useRouter } from "vue-router";
const router = useRouter();

const type = ref(0);
const name = ref("");
const id = ref("");
const password = ref("");
const nth = ref(11);
const campus = ref("");
const dept = ref(0);
const floor = ref(0);
const adcode = ref("");

// Id 체크하는 변수
const idCheckToggle = ref(false);
const contentCheckToggle = ref(false);

const checkIdRes = (e) => {
  if (e) {
    alert("사용 가능한 아이디입니다.");
  } else {
    alert("중복된 아이디가 있습니다. 다시 시도하세요.");
  }
};

// Id 체크하는 함수
const checkId = (e) => {
  axios
    .get(import.meta.env.VITE_SERVER_MAIN_URL + "/user/join/valID", {
      params: { id: id.value },
      headers: {
        "Content-Type": "multipart/form-data",
        "ngrok-skip-browser-warning": "69420",
      },
    })
    .then((res) => {
      console.log(res.data);
      idCheckToggle.value = res.data;
      checkIdRes(idCheckToggle.value);
    });
};

// 회원가입 폼 보내는 함수
const joinSend = (e) => {
  if (type.value == "0") {
    //회원일때
    if (
      name.value != "" &&
      id.value != "" &&
      password.value != "" &&
      nth.value != "" &&
      campus.value != "" &&
      dept.value != "" &&
      floor.value != ""
    ) {
      contentCheckToggle.value = true;
    }
  } else if (type.value == "1") {
    //관리자일때
    if (
      name.value != "" &&
      id.value != "" &&
      password.value != "" &&
      campus.value != "" &&
      adcode.value != ""
    ) {
      contentCheckToggle.value = true;
    }
  }

  if (contentCheckToggle.value) {
    const joinForm = new FormData();
    joinForm.append("id", id.value);
    joinForm.append("password", password.value);
    joinForm.append("type", type.value);
    joinForm.append("nth", nth.value);
    joinForm.append("floor", floor.value);
    joinForm.append("dept", dept.value);
    joinForm.append("name", name.value);
    joinForm.append("campus", campus.value);
    joinForm.append("adcode", adcode.value);
    console.log("joinForm ====>", joinForm);

    axios
      .post(import.meta.env.VITE_SERVER_MAIN_URL + "/user/join", joinForm, {
        headers: {
          "Content-Type": "multipart/form-data",
        },
      })
      .then((res) => {
        console.log(res);
        router.push("/login");
      })
      .catch((res) => {
        alert("빈 항목을 채우고 다시 시도해주세요.");
      })
      .finally((res) => {
        contentCheckToggle.value = false;
      });
  }
};
</script>

<style scoped>
table {
  width: 200px;
  height: 500px;
  margin: auto;
}

.email {
  width: 127px;
  height: 32px;
  font-size: 15px;
  border: 0;
  border-color: lightgray;
  border-radius: 15px;
  outline: none;
  padding-left: 10px;
  background-color: rgb(233, 233, 233);
}

.text {
  width: 200px;
  height: 32px;
  font-size: 15px;
  border: 0;
  border-radius: 15px;
  outline: none;
  padding-left: 10px;
  background-color: rgb(233, 233, 233);
}

.text2 {
  width: 100px;
  height: 32px;
  font-size: 15px;
  border: 0;
  border-radius: 15px;
  outline: none;
  padding-left: 10px;
  background-color: rgb(233, 233, 233);
}

select {
  width: 100px;
  height: 32px;
  font-size: 15px;
  border: 1;
  border-color: lightgray;
  border-radius: 15px;
  outline: none;
  padding-left: 10px;
}

.btn {
  width: 262px;
  height: 32px;
  font-size: 15px;
  border: 0;
  border-radius: 15px;
  outline: none;
  padding-left: 10px;
  background-color: rgb(164, 199, 255);
}

.btn:active {
  width: 262px;
  height: 32px;
  font-size: 15px;
  border: 0;
  border-radius: 15px;
  outline: none;
  padding-left: 10px;
  background-color: rgb(61, 135, 255);
}

.btn:disabled {
  border: 1px solid #999999;
  background-color: #cccccc;
  color: #666666;
}

.th {
  display: flex;
  align-items: space-between;
}

.th>div {
  display: flex;
  flex-direction: column;
}

input.text {
  width: 230px !important;
}
</style>
