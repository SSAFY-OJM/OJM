// store.js
import { createStore } from "vuex";

export const store = createStore({
  state: {
    token: localStorage.getItem("token") || null,
    type: localStorage.getItem("type") || null,
    campus: localStorage.getItem("campus") || null,
    dept: localStorage.getItem("dept") || null,
    floor: localStorage.getItem("floor") || null,
    userId: localStorage.getItem("id") || null,
    name: localStorage.getItem("name") || null,
    nth: localStorage.getItem("nth") || null,
  },
  mutations: {
    setToken(state, token) {
      state.token = token;
    },

    setUserType(state, type) {
      state.type = type;
    },
    setUserCampus(state, campus) {
      state.campus = campus;
    },
    setUserDept(state, dept) {
      state.dept = dept;
    },
    setUserFloor(state, floor) {
      state.floor = floor;
    },
    setUserId(state, id) {
      state.userId = id;
    },
    setUserName(state, name) {
      state.name = name;
    },
    setUserNth(state, nth) {
      state.nth = nth;
    },
    clearAuthData(state) {
      // state.token = null;
      state.userId = null;
    },
  },
  actions: {
    login({ commit }, authData) {
      localStorage.setItem("campus", authData.campus);
      localStorage.setItem("dept", authData.dept);
      localStorage.setItem("floor", authData.floor);
      localStorage.setItem("id", authData.id);
      localStorage.setItem("name", authData.name);
      localStorage.setItem("nth", authData.nth);
      localStorage.setItem("type", authData.type);
      localStorage.setItem("token", authData.token);

      commit("setToken", authData.token);
      commit("setUserCampus", authData.campus);
      commit("setUserDept", authData.dept);
      commit("setUserFloor", authData.floor);
      commit("setUserName", authData.name);
      commit("setUserNth", authData.nth);
      commit("setUserType", authData.type);
      commit("setUserId", authData.id);
    },

    logout({ commit }) {
      localStorage.removeItem("token");
      localStorage.removeItem("campus");
      localStorage.removeItem("dept");
      localStorage.removeItem("floor");
      localStorage.removeItem("id");
      localStorage.removeItem("name");
      localStorage.removeItem("nth");
      localStorage.removeItem("type");
      localStorage.removeItem("token");
      commit("clearAuthData");
    },
  },
  getters: {
    isAuthenticated(state) {
      return state.token !== null;
    },
    token(state) {
      return state.token;
    },

    userCampus(state) {
      return state.campus;
    },
    userDept(state) {
      return state.dept;
    },
    userFloor(state) {
      return state.floor;
    },
    userName(state) {
      return state.name;
    },
    userNth(state) {
      return state.nth;
    },
    userType(state) {
      return state.type;
    },
    userId(state) {
      return state.userId;
    },
  },
});
