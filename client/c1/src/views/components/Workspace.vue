<template>
  <div id="option">
    <template v-if="getUsername && getToken">
      <el-card v-for="url in urls" :key="url.id">
        <div slot="header" class="clearfix">
          <span>{{ url.title }}</span>
          <el-button
            style="float: right; padding: 3px 0"
            type="text"
            @click="move(url.url)"
            >이동하기</el-button
          >
          {{ url.type }}
        </div>
      </el-card>
    </template>
    <template v-else>
      <div class="no-info">
        <i class="el-icon-s-promotion icon"></i>
        <h2>URL 입력은 로그인을 해야 가능합니다.</h2>
        <p>로그인을 진행해주세요</p>
        <router-link to="/login">
          <el-button>바로가기</el-button>
        </router-link>
      </div>
    </template>
  </div>
</template>

<script>
import {mapGetters} from 'vuex';
import mainApi from '../../api/mainApi';
import {mixin} from '../../utils/mixin';

export default {
  data() {
    return {
      urls: [],
    };
  },

  computed: {
    ...mapGetters(['getToken', 'getUsername']),
  },

  methods: {
    initComponent() {
      this.urls = this.$store.getters.getUrls;
      const payload = {
        urls: this.urls,
      };
      this.save(payload, 'updated');
    },
    async inject() {
      const response = await mainApi.inject();
      if (response.status === 200) {
        this.saved = true;
      } else {
        this.net_false = true;
      }
    },
    move(url) {
      window.open(url, '_blank');
    },
  },

  mixins: [mixin],
};
</script>

<style lang="scss" scoped>
#option {
  position: relative;
  .no-info {
    display: flex;
    justify-content: center;
    vertical-align: middle;
    flex-direction: column;
    text-align: center;
    height: 230px;

    .icon {
      font-size: 30px;
    }
  }
}
.text {
  font-size: 14px;
}

.item {
  margin-bottom: 18px;
}

.clearfix:before,
.clearfix:after {
  display: table;
  content: '';
}
.clearfix:after {
  clear: both;
}
</style>
