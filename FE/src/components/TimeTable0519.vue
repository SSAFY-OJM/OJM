<template>
  <div class="table-container h-[250px]">
    <table
      class="type07"
      :style="getTableHeaderStyle()"
      style="position: relative"
    >
      <thead>
        <tr>
          <th scope="cols"><br />층/시간</th>
          <th scope="cols">
            회원님의 점심시간은 {{ floor }}층
            <br />
            <br />{{ times[findTime(floor)] }} 입니다.
          </th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="floor in floors" :key="floor.key">
          <th scope="row">{{ floor.key }} 층</th>
          <td
            :style="[
              getCellStyle(false),
              upfloorKey == floor.key ? selectedTimeStyle : {},
            ]"
          >
            {{ times[floor.index] }}
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script setup>
import { onMounted, ref, computed } from "vue";
import { useStore } from "vuex";
const store = useStore();
const floor = computed(() => store.getters.userFloor);
// const token = computed(() => store.getters.token);
const times = ref([
  "12 : 00 - 13 : 00",
  "12 : 15 - 13 : 15",
  "12 : 30 - 13 : 30",
  "12 : 45 - 13 : 45",
  "13 : 00 - 14 : 00",
]);

const floors = ref([
  { key: 5, index: 0 },
  { key: 3, index: 1 },
  { key: 2, index: 2 },
  { key: 7, index: 3 },
  { key: 6, index: 4 },
]);

const findTime = (index) => {
  let val = 0;
  floors.value.map((each) =>
    each.key == index ? (val = each.index) : (val = val)
  );
  return val;
};

const num = floors.value.length;

let floorrrr = ref(0); //내가 속한 층의 인덱스
let upfloorKey = ref(3); // <<< 여기로 회원 층 정보 주기

const upfloor = () => {
  floorrrr.value = (floorrrr.value + 1) % floors.value.length;
  for (let i = 0; i < floors.value.length; i++) {
    let floor = floors.value[i];
    if (floor.index === floorrrr.value) {
      upfloorKey.value = floor.key;
      console.log(floor.key);
    }
  }
};

let floorrrrTime = computed(() => {
  return floors.value.findIndex((floor) => floor.key === floorrrr.value);
});

onMounted(() => {
  let diff = new Date().getMonth() + 1 - 1;
  floors.value.map((each) => {
    each.index += diff;
    each.index = each.index % num;
  });

  floors.value = floors.value.sort((a, b) => a.index - b.index);
});

const getTableHeaderStyle = () => {
  return {
    textAlign: "center",
    verticalAlign: "middle",
    border: "1px solid #ccc",
  };
};

const getCellStyle = (isHeader) => {
  const style = {
    textAlign: "center",
    border: "1px solid #ccc",
  };

  if (isHeader) {
    style.fontWeight = "bold";
  }

  return style;
};

const selectedTimeStyle = {
  // border: "1px solid rgba(49, 130, 246, 0.6)",
  textAlign: "center",
};
</script>

<style>
.table-container {
  border-radius: 12px;
  overflow: hidden;
  border: 1px solid #ccc;
  margin: 20px 10px;
}

table.type07 {
  border-collapse: collapse;
  text-align: center;
  justify-content: center;
  line-height: 1;
  width: 100%;
}

table.type07 thead {
  background: #3677ba;
  border-left: 1px solid #ccc;
  border-right: 1px solid #ccc;
}

table.type07 thead th {
  padding: 10px;
  /* font-weight: bold; */
  vertical-align: top;
  color: #fff;
}

table.type07 tbody th {
  width: 150px;
  padding: 10px;
  /* font-weight: bold; */
  vertical-align: top;
  background: #dcf0fa;
}

table.type07 td {
  width: 350px;
  padding: 10px;
  vertical-align: top;
}
</style>
