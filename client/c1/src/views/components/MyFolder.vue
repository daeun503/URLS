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
                  {{ index }}
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
            v-for="(folder, index) in folders"
            :key="index"
            placement="top"
          >
            <el-card :body-style="{padding: '10px'}">
              <span slot="header">
                <el-button type="text">
                  {{ index }}
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
      </el-row></template
    >
  </div>
</template>

<script>
import {mixin} from '../../utils/mixin';

export default {
  data() {
    return {
      folders: [],
      folders_id: '',
      folders_name: '',
      loading: false,
      move: true,
    };
  },

  methods: {
    initComponent() {
      this.folders = this.$store.getters.getFolders;
    },
    change(id, name) {
      this.folders_id = id;
      this.folders_name = name;
      this.move = !this.move;
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
