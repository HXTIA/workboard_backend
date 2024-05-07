package run.hxtia.workbd.service.admin.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import run.hxtia.workbd.common.enhance.MpLambdaQueryWrapper;
import run.hxtia.workbd.common.mapstruct.MapStructs;
import run.hxtia.workbd.common.util.Constants;
import run.hxtia.workbd.common.util.Streams;
import run.hxtia.workbd.mapper.ResourceMapper;
import run.hxtia.workbd.pojo.dto.ResourceDto;
import run.hxtia.workbd.pojo.po.Resource;
import run.hxtia.workbd.service.admin.ResourceService;
import run.hxtia.workbd.service.admin.RoleResourceService;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ResourceServiceImpl
    extends ServiceImpl<ResourceMapper, Resource> implements ResourceService {

    private final RoleResourceService roleResourceService;

    /**
     * 根据角色ID构建树状结构的菜单
     * @param roleStrIds：所有角色ID
     * @return ：树状结构的菜单
     */
    @Override
    public List<ResourceDto> listMenu(String roleStrIds) {
        if (!StringUtils.hasLength(roleStrIds)) return null;

        // 解析角色IDString
        List<Short> roleIds = Streams.list2List(Arrays.asList(roleStrIds.split(",")), Short::valueOf);
        // 获取所有角色对应的资源ID【去重后的】
        List<Short> resourceIds = roleResourceService.listResourceIds(roleIds);
        // 获取排序后的资源
        List<Resource> resources = listOrderByType(resourceIds);
        // 构建资源树
        return listComTree(resources);
    }

    /**
     * 构建完整的资源树结构
     * @return ：一整棵父子结构
     */
    @Override
    public List<ResourceDto> listAllTree() {
        MpLambdaQueryWrapper<Resource> wrapper = new MpLambdaQueryWrapper<>();
        wrapper.orderByAsc(Resource::getType);
        return listComTree(baseMapper.selectList(wrapper));
    }

    /**
     * 根据角色ID查询对应的资源，并且排序
     * @param resIds：资源ID
     * @return ：排序后的资源
     */
    private List<Resource> listOrderByType(List<Short> resIds) {
        if (CollectionUtils.isEmpty(resIds)) return null;
        List<Resource> resources = listByIds(resIds);
        // 按照类型排序
        resources.sort(Comparator.comparingInt(Resource::getType));
        return resources;
    }

    /**
     * 构建树结构的资源目录
     * @param resources：拥有的资源
     * @return ：树状结构的资源
     */
    private List<ResourceDto> listComTree(List<Resource> resources) {
        if (CollectionUtils.isEmpty(resources)) return null;

        // 最终返回的tree结构
        List<ResourceDto> tree = new ArrayList<>();
        // 用Map先将每一项放入，如果不是目录，那么可以直接从Map里取出此项的父级资源
        Map<Short, ResourceDto> dtoMap = new HashMap<>();

        for (Resource resource : resources) {
            ResourceDto resDto = MapStructs.INSTANCE.po2dto(resource);

            // 直接放入dtoMap中
            dtoMap.put(resource.getId(), resDto);

            Short type = resDto.getType();
            if (type.equals(Constants.Status.DIR_TYPE)) {
                // 如果是目录.直接添加到返回结果中即可
                tree.add(resDto);
            } else {
                // 来到这里说明是菜单或者按钮，那么添加到父级目录的子资源里
                ResourceDto parent = dtoMap.get(resDto.getParentId());
                List<ResourceDto> children = parent.getChildren();
                if (children == null) {
                    // 将其设置为null集合
                    parent.setChildren(children = new ArrayList<>());
                }
                children.add(resDto);
            }
        }
        return tree;
    }

    /**
     * 根据角色IDs获取资源列表
     * @param roleIds：角色IDs
     * @return ：资源列表
     */
    @Override
    public List<Resource> listByRoleIds(List<Short> roleIds) {
        if (CollectionUtils.isEmpty(roleIds)) return null;

        List<Short> resourceIds = roleResourceService.listResourceIds(roleIds);
        if (CollectionUtils.isEmpty(resourceIds)) return null;

        return baseMapper.selectBatchIds(resourceIds);
    }
}
