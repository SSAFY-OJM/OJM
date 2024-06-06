<template>
  <div class="w-full p-10 justify-center items-center">
    <form class="flex justify-center items-center">
      <table>
        <tr>
          <td>
            <h2>로그인</h2>
          </td>
        </tr>
        <tr>
          <td>아이디</td>
        </tr>
        <tr>
          <td><input type="text" class="text" v-model.trim="id" /></td>
        </tr>
        <tr>
          <td>비밀번호</td>
        </tr>
        <tr>
          <td>
            <input type="password" class="text" v-model.trim="password" />
          </td>
        </tr>
        <tr>
          <td>
            <button
              type="button"
              class="btn"
              @click="checkLogin"
              style="width: 120px"
            >
              로그인
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
import { useStore } from "vuex";
const id = ref("");
const password = ref("");
const router = useRouter();
const store = useStore();
const checkLogin = (e) => {
  // if (type.value == "0") { //회원일때
  //     if (name.value != '' && id.value != '' && password.value != '' && nth.value != '' && campus.value != '' && dept.value != '' && floor.value != '') {
  //         contentCheckToggle.value = true
  //     }
  // } else if (type.value == "1") { //관리자일때
  //     if (name.value != '' && id.value != '' && password.value != '' && campus.value != '' && adcode.value != '') {
  //         contentCheckToggle.value = true
  //     }
  // }

  // if (contentCheckToggle.value) {
  const loginForm = new FormData();
  loginForm.append("id", id.value);
  loginForm.append("password", password.value);

  axios
    .post(import.meta.env.VITE_SERVER_MAIN_URL + "/user/login", loginForm, {
      headers: {
        "Content-Type": "multipart/form-data",
      },
    })
    .then((res) => {
      if (id.value === "" || password.value === "") {
        alert("빈 항목을 채우고 다시 시도해주세요.");
        return;
      }
      const authData = res.data;
      authData.token = true;
      localStorage.setItem("campus", authData.campus);
      localStorage.setItem("dept", authData.dept);
      localStorage.setItem("floor", authData.floor);
      localStorage.setItem("id", authData.id);
      localStorage.setItem("name", authData.name);
      localStorage.setItem("nth", authData.nth);
      localStorage.setItem("type", authData.type);
      localStorage.setItem("token", true);

      // const token = res.data;
      store.dispatch("login", authData);
      if (authData.type) {
        router.push("/");
      } else {
        router.push("/");
      }
    })
    .catch((res) => {
      alert("아이디 또는 비밀번호가 일치하지 않습니다.\n다시 시도해주세요.");
    })
    .finally((res) => {
      // contentCheckToggle.value = false
    });
  // }
};
</script>

<style scoped>
table {
  width: 200px;
  height: 300px;
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

.th > div {
  display: flex;
  flex-direction: column;
}

input.text {
  width: 230px !important;
}
</style>
