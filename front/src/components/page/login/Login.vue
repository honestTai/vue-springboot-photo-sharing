<template>
  <div class="login-wrap">
    <div class="ms-login">
      <div class="ms-title">共享图片系统后台管理</div>
      <el-form :model="param" :rules="rules" ref="login" label-width="0px" class="ms-content">
        <el-form-item prop="num">
          <el-input v-model="param.num" placeholder="num">
            <el-button slot="prepend" icon="el-icon-lx-people"></el-button>
          </el-input>
        </el-form-item>
        <el-form-item prop="pwd">
          <el-input
              type="password"
              placeholder="password"
              v-model="param.pwd"
              show-password
          >
            <el-button slot="prepend" icon="el-icon-lx-lock"></el-button>
          </el-input>
        </el-form-item>
        <div class="login-btn">
          <el-button type="primary" @click="submitForm()">登录</el-button>
        </div>
        <p class="login-tips">Tips : 请输入用户名与密码进行登录。</p>
      </el-form>
    </div>
  </div>
</template>

<script>
import { login } from '../../../utils';

export default {
  data: function() {
    return {
      param: {
        username: '',
        password: '',
        secCode: ''
      },
      checkCode: '',
      rules: {
        username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
        password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
      }
    };
  },
  methods: {
    //提交
    submitForm() {
      this.$refs.login.validate(valid => {
        if (valid) {
          login({
            num: this.param.num,
            pwd: this.param.pwd
          }).then((res) => {
            let data = res.data;
            if (data.code == 200) {
              //userInfo存入缓存
              localStorage.setItem('userInfo', JSON.stringify(data.data.userInfo));
              localStorage.setItem('token', data.data.loginUserCode);
              this.$message.success(data.msg);
              this.$router.push('/');
            } else {
              this.$message.error(data.msg);
              return false;
            }
          });
        } else {
          return false;
        }
      });
    }
  }
};
</script>

<style scoped>
.login-wrap {
  position: relative;
  width: 100%;
  height: 100%;
  background-image: url(../../../assets/logo.jpg);
  background-size: 100%;
}

.ms-title {
  width: 100%;
  line-height: 50px;
  text-align: center;
  font-size: 20px;
  color: black;
  border-bottom: 1px solid #ddd;
}

.ms-login {
  position: absolute;
  left: 50%;
  top: 50%;
  width: 350px;
  margin: -190px 0 0 -175px;
  border-radius: 5px;
  background: rgba(255, 255, 255, 0.3);
  overflow: hidden;
}

.ms-content {
  padding: 30px 30px;
}

.login-btn {
  text-align: center;
}

.login-btn button {
  width: 100%;
  height: 36px;
  margin-bottom: 10px;
}

.login-tips {
  font-size: 12px;
  line-height: 30px;
  color: red;
}
</style>
