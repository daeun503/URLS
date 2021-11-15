<template>
  <div id="releases">
    <template v-if="move">
      <el-row>
        <el-timeline>
          <el-timeline-item
            v-for="(folder, index) in folders"
            :key="index"
            placement="top"
          >
            <el-card :body-style="{padding: '10px'}">
              <span slot="header">
                <el-button type="text">
                  {{ index }}. {{ folder.folder_name }}
                </el-button>
              </span>
              <h5>{{ folder.folder_id }}</h5>
              <div>
                <el-button
                  type="text"
                  @click="change(folder.folder_id, folder.folder_name)"
                >
                  이동하기
                </el-button>
              </div>
            </el-card>
          </el-timeline-item>
        </el-timeline>
      </el-row>
    </template>
    <template v-else>
      <h3>{{ this.folders_name }} - {{ this.folders_id }}</h3>
      <el-row>
        <el-timeline>
          <el-timeline-item
            v-for="(url, index) in urls"
            :key="index"
            placement="top"
          >
            <el-card :body-style="{padding: '10px'}">
              <span slot="header">
                <a :href="url.url" target="_blank">
                  <el-button type="text" style="margin-left: 10px">
                    {{ index }}. {{ url.url }}
                  </el-button>
                </a>
              </span>

              <h5>
                {{ url.url }}
              </h5>
              <a href="https://k5b201.p.ssafy.io/" target="_blank">
                <el-button> 관리 페이지 이동하기</el-button>
              </a>
            </el-card>
          </el-timeline-item>
        </el-timeline>
      </el-row></template
    >
  </div>
</template>

<script>
import mainApi from '../../api/mainApi';
import {mixin} from '../../utils/mixin';

export default {
  data() {
    return {
      folders: [],
      folders_id: '',
      folders_name: '',
      loading: false,
      move: true,
      urls: [],
    };
  },

  methods: {
    initComponent() {
      this.folders = this.$store.getters.getFolders;
    },
    async change(id, name) {
      this.folders_id = id;
      this.folders_name = name;
      this.move = !this.move;

      const token = await mainApi.getFromStorage('token');
      const response = await mainApi.getUrls(token, id);
      if (response && response.data) {
        if (response.status === 200) {
          console.log('url 확인하기');
          console.log(response.data);
          this.urls = response.data.urls;
        }
      }
    },
  },

  mixins: [mixin],
};
</script>

<style lang="scss" scoped>
#releases {
  min-height: 170px;

  .el-timeline {
    padding-left: 0;

    /deep/ .el-card__header {
      padding: 0 10px;
    }
  }

  .release-html {
    word-break: keep-all;

    /deep/ ul {
      padding-left: 20px;
    }
  }
}
</style>
