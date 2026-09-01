<template>
  <div class="app-container">

    <!-- 搜索表单 -->
    <el-form ref="queryRef" :inline="true" label-width="68px">
      <el-form-item label="名称" prop="name">
        <el-input
            v-model="queryParams.name"
            placeholder="请输入名称"
            clearable
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <!-- 功能按钮栏 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
            @click="handleAdd"
            type="primary"
            plain
            icon="Plus"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="success"
            plain
            icon="Edit"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="danger"
            plain
            icon="Delete"
        >删除</el-button>
      </el-col>
    </el-row>

    <!-- 数据展示表格 -->
    <el-table :data="cabinetTypeList">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="名称" prop="name" width="150"/>
      <el-table-column label="总插槽数量" prop="totalSlots" width="110"/>
      <el-table-column label="描述" prop="description" />
      <el-table-column label="状态" prop="status" width="100">
        <template #default="scope">
          {{ scope.row.status == '1' ? '正常' : '停用' }}
        </template>
      </el-table-column>
      <el-table-column prop="createTime" label="创建时间" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="deleteData(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页条组件 -->
    <pagination
        v-show="total > 0"
        :total="total"
        v-model:page="queryParams.pageNum"
        v-model:limit="queryParams.pageSize"
        @pagination="getList"
    />

    <!-- 新增弹框组件 -->
    <el-dialog :title="title" v-model="open" width="600px" append-to-body>
        <el-form ref="cabinetTypeRef" :model="form" :rules="rules" label-width="120px">
            <el-form-item label="名称" prop="name">
            <el-input v-model="form.name" placeholder="请输入名称" />
            </el-form-item>
            <el-form-item label="总插槽数量" prop="totalSlots">
            <el-select
                v-model="form.totalSlots"
                class="m-2"
                placeholder="请选择总插槽数量"
                style="width: 100%"
            >
                <el-option
                    v-for="item in 20"
                    :key="item"
                    :label="item"
                    :value="item"
                />
            </el-select>
            </el-form-item>
            <el-form-item label="描述" prop="description">
            <el-input v-model="form.description" type="textarea" placeholder="请输入内容" />
            </el-form-item>
        </el-form>
        <template #footer>
            <div class="dialog-footer">
            <el-button type="primary" @click="submitForm">确 定</el-button>
            <el-button @click="cancel">取 消</el-button>
            </div>
        </template>
    </el-dialog>

  </div>
</template>

<script setup name="CabinetType">
// 引入api接口
import {listCabinetType,addCabinetType} from "@/api/device/cabinetType";
//引入ElMessage组件
import { ElMessage } from "element-plus";
import { toRefs } from "vue";

//定义分页列表数据模型
const cabinetTypeList = ref([
//   {
//     "id": 1,
//     "name": "八口柜机",
//     "totalSlots": 8,
//     "description": "八口柜机八",
//     "status": "1",
//     "createTime": "2024-01-09 10:15:05"
//   },
//   {
//     "id": 1,
//     "name": "十二口柜机",
//     "totalSlots": 12,
//     "description": "十二口柜机",
//     "status": "1",
//     "createTime": "2024-01-09 10:15:05"
//   }
]);
//定义列表总记录数模型
// const total = ref(2);
const total = ref(0);
//加载数据时显示的动效控制模块
const loading = ref(true);

//弹框
const open = ref(false);

//弹出框页面显示名称
const title = ref("");

// Vue 3 中的两种响应式数据绑定方式： reactive 和 ref
// ref定义：基本数据类型，适用于简单的响应式数据
//reactive定义：对象（或数组）数据类型，则适用于复杂对象或数组的响应式数据
const data = reactive({
    //定义搜索模型
    queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: null
    },
    //封装表单数据
    form: {}
});
//toRefs 是一个Vue3中提供的API，可将一个响应式对象转换为普通对象，其中属性变成了对原始对象属性的引用
const {queryParams,form} = toRefs(data);

///////////////////////////////////////////===修改===//////////////////////////////////////////////////////////////////
function handleUpdate(row){

}
///////////////////////////////////////////===添加===//////////////////////////////////////////////////////////////////
//表单重置
function reset(){
    form.value = {
        id: null,
        name: null,
        totalSlots: null,
        description: null,
        status: null,
        remark: null

    };
}
//新增按钮操作
function handleAdd(){
    reset();
    open.value = true;
    title.value = "添加柜机类型";
}
//取消操作
function cancel(){
    open.value = false;
    reset();
}
//添加方法
function submitForm(){
    addCabinetType(form.value).then(response =>{
        ElMessage.success("新增成功");
        open.value = false;
        getList();
    });
    

}

////////////////////////////////////////////====分页列表===/////////////////////////////////////////////////////////////////

/** 查询柜机类型列表 */
function getList(){
    loading.value = true;
    listCabinetType(queryParams.value).then(response =>{
        cabinetTypeList.value = response.rows;
        total.value = response.total;
        loading.value = false;
    });
}

// 搜索按钮操作
function handleQuery(){
    getList()
}

//重置按钮操作
function resetQuery(){
    queryParams.value.pageNum = 1
    queryParams.value.pageSize = 10
    queryParams.value.name = null
    handleQuery();
}

//执行查询柜机类型列表
getList()
</script>