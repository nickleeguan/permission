package com.icode.service.impl;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.icode.dao.SysAclModuleMapper;
import com.icode.dao.SysDeptMapper;
import com.icode.dto.AclModuleLevelDto;
import com.icode.dto.DeptLevelDto;
import com.icode.model.SysAclModule;
import com.icode.model.SysDept;
import com.icode.service.interfaces.SysTreeService;
import com.icode.util.LevelUtil;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class SysTreeServiceImpl implements SysTreeService {

    private static final Logger logger = LoggerFactory.getLogger(SysTreeServiceImpl.class);

    @Resource
    private SysAclModuleMapper sysAclModuleMapper;

    @Resource
    private SysDeptMapper sysDeptMapper;

    public List<AclModuleLevelDto> aclModuleTree(){
        List<SysAclModule> aclModuleList = sysAclModuleMapper.getAllAclModule();

        List<AclModuleLevelDto> dtoList = Lists.newArrayList();

        for (SysAclModule aclModule:aclModuleList){
            dtoList.add(AclModuleLevelDto.adapt(aclModule));
        }
        return aclModuleListToTree(dtoList);
    }

    public List<AclModuleLevelDto> aclModuleListToTree(List<AclModuleLevelDto> dtoList){
        if (CollectionUtils.isEmpty(dtoList)){
            return Lists.newArrayList();
        }

        Multimap<String, AclModuleLevelDto> levelAclModuleMap = ArrayListMultimap.create();
        List<AclModuleLevelDto> rootList = Lists.newArrayList();

        for (AclModuleLevelDto dto : dtoList){
            levelAclModuleMap.put(dto.getLevel(), dto);
            if (LevelUtil.ROOT.equals(dto.getLevel())){
                rootList.add(dto);
            }
        }
        Collections.sort(rootList, aclModuleSeqComparator);

        transformAclModuleTree(rootList, LevelUtil.ROOT, levelAclModuleMap);

        return rootList;
    }

    public void transformAclModuleTree(List<AclModuleLevelDto> dtoList, String level,
                                       Multimap<String, AclModuleLevelDto> levelAclModuleMap){

        for (int i = 0; i < dtoList.size(); i++){
            AclModuleLevelDto dto = dtoList.get(i);
            String nextLevel = LevelUtil.calculateLevel(level, dto.getId());
            List<AclModuleLevelDto> tempList = (List<AclModuleLevelDto>) levelAclModuleMap.get(nextLevel);
            if (!CollectionUtils.isEmpty(tempList)){
                Collections.sort(tempList, aclModuleSeqComparator);
                dto.setAclModuleList(tempList);
                transformAclModuleTree(tempList, nextLevel, levelAclModuleMap);
            }
        }
    }



    public List<DeptLevelDto> deptTree(){
        List<SysDept> deptList = sysDeptMapper.getAllDept();
        List<DeptLevelDto> dtoList = Lists.newArrayList();

        for (SysDept dept : deptList) {
            DeptLevelDto dto  = DeptLevelDto.adapt(dept);
            dtoList.add(dto);
        }

        return deptListToTree(dtoList);
    }

    public List<DeptLevelDto> deptListToTree(List<DeptLevelDto> deptLevelList) {
        if (CollectionUtils.isEmpty(deptLevelList)){
            return Lists.newArrayList();
        }
        // level -> [dept1, dept2, ...]
        Multimap<String, DeptLevelDto> levelDeptMap = ArrayListMultimap.create();
        List<DeptLevelDto> rootList = Lists.newArrayList();

        for (DeptLevelDto dto : deptLevelList) {
            levelDeptMap.put(dto.getLevel(), dto);
            if (LevelUtil.ROOT.equals(dto.getLevel())){
                rootList.add(dto);
            }
        }

        //按照seq从小到大的排序
        Collections.sort(rootList, new Comparator<DeptLevelDto>() {
            public int compare(DeptLevelDto o1, DeptLevelDto o2) {
                return o1.getSeq() - o2.getSeq();
            }
        });
        //递归生成树
        transformDeptTree(rootList, LevelUtil.ROOT, levelDeptMap);
        return rootList;
    }

    public void transformDeptTree(List<DeptLevelDto> deptLevelList, String level,
                                  Multimap<String, DeptLevelDto> levelDeptMap){

        for (int i = 0; i < deptLevelList.size(); i++){
            //遍历盖层每个元素
            DeptLevelDto deptLevelDto = deptLevelList.get(i);
            //处理该层层级
            String nextLevel = LevelUtil.calculateLevel(level, deptLevelDto.getId());
            //处理下一层
            List<DeptLevelDto> tempDeptList = (List<DeptLevelDto>) levelDeptMap.get(nextLevel);
            if (CollectionUtils.isNotEmpty(tempDeptList)){
                //排序
                Collections.sort(tempDeptList, deptSeqComparator);
                //设置下一层部门
                deptLevelDto.setDeptList(tempDeptList);
                //进入下一层处理
                transformDeptTree(tempDeptList, nextLevel, levelDeptMap);
            }
        }
    }

    public Comparator<DeptLevelDto> deptSeqComparator = new Comparator<DeptLevelDto>() {
        public int compare(DeptLevelDto o1, DeptLevelDto o2) {
            return o1.getSeq() - o2.getSeq();
        }
    };

    /**
     * 比较器
     * @return
     */
    public Comparator<AclModuleLevelDto> aclModuleSeqComparator = new Comparator<AclModuleLevelDto>() {
        public int compare(AclModuleLevelDto o1, AclModuleLevelDto o2) { return o1.getSeq() - o2.getSeq();
        }
    };
}
