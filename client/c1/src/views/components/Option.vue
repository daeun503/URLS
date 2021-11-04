<template>
  <div id="option">
    <el-row>
      <el-form ref="form" label-width="80px" label-position="left" size="small">
        <el-form-item label="url">
          <el-input
            v-model="url"
            size="small"
            placeholder="url을 입력해주세요"
          ></el-input>
        </el-form-item>
        <el-form-item label="카테고리">
          <el-select v-model="serviceType" placeholder="카테고리를 적어주세요">
            <el-option-group
              v-for="group in serviceTypes"
              :key="group.label"
              :label="group.label"
            >
              <el-option
                v-for="item in group.options"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              >
                <span style="float: left">{{ item.label }}</span>
              </el-option>
            </el-option-group>
          </el-select>
        </el-form-item>
      </el-form>
      <el-button
        :type="saved ? 'success' : 'primary'"
        size="small"
        @click="inject"
      >
        <span v-if="saved">
          <i class="el-icon-check"></i>
          URL 저장됨
        </span>
        <span v-else-if="net_false">네트워크를 확인해주세요</span>
        <span v-else>URL 저장</span>
      </el-button>
    </el-row>
  </div>
</template>

<script>
import {mapGetters} from 'vuex';
import mainApi from '../../api/mainApi';
import {CATEGORY} from '../../utils';
import {mixin} from '../../utils/mixin';

export default {
  data() {
    return {
      url: null,
      serviceType: null,
      serviceTypes: CATEGORY,
      saved: false,
      net_false: false,
    };
  },

  computed: {
    ...mapGetters(['getToken']),
  },

  methods: {
    initComponent() {},
    async inject() {
      const response = await mainApi.inject();
      if (response.status === 200) {
        this.saved = true;
      } else {
        this.net_false = true;
      }
    },
  },

  mixins: [mixin],
};
</script>

<style lang="scss" scoped>
#option {
  position: relative;
}
</style>
