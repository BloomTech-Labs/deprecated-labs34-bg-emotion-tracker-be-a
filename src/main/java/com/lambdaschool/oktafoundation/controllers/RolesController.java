package com.lambdaschool.oktafoundation.controllers;

import com.lambdaschool.oktafoundation.exceptions.ResourceNotFoundException;
import com.lambdaschool.oktafoundation.models.ErrorDetail;
import com.lambdaschool.oktafoundation.models.Role;
import com.lambdaschool.oktafoundation.services.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

/**
 * The entry point for clients to access role data
 * <p>
 * Note: we cannot update a role
 * we cannot update a role
 * working with the "non-owner" object in a many to many relationship is messy
 * we will be fixing that!
 */
@RestController
@Api (value ="/roles")
public class RolesController
{
    /**
     * Using the Role service to process Role data
     */
    @Autowired
    RoleService roleService;

    /**
     * List of all roles
     * <br>Example: <a href="http://localhost:2019/roles/roles">http://localhost:2019/roles/roles</a>
     *
     * @return JSON List of all the roles and their associated users
     * @see RoleService#findAll() RoleService.findAll()
     */
    @RequestMapping(value = "/roles", method = RequestMethod.GET, produces = "application/json")
    @ApiOperation(value = "returns all the roles",
        response = Role.class,
        responseContainer = "List")
    public ResponseEntity<?> listRoles()
    {
        List<Role> allRoles = roleService.findAll();
        return new ResponseEntity<>(allRoles,
            HttpStatus.OK);
    }

    /**
     * The Role referenced by the given primary key
     * <br>Example: <a href="http://localhost:2019/roles/role/3">http://localhost:2019/roles/role/3</a>
     *
     * @param roleId The primary key (long) of the role you seek
     * @return JSON object of the role you seek
     * @see RoleService#findRoleById(long) RoleService.findRoleById(long)
     */
    @RequestMapping(value = "/role/{roleId}", method = RequestMethod.GET, produces = "application/json")
    @ApiOperation(value = "returns a role with the path parameter of roleId")
    @ApiResponses(value = {
        @ApiResponse(code = 200,
            message = "Role Found",
            response = Role.class),
        @ApiResponse(code = 404,
            message = "Role Not Found",
            response = ResourceNotFoundException.class)})
    public ResponseEntity<?> getRoleById(
        @PathVariable
            Long roleId)
    {
        Role r = roleService.findRoleById(roleId);
        return new ResponseEntity<>(r,
            HttpStatus.OK);
    }

    /**
     * The Role with the given name
     * <br>Example: <a href="http://localhost:2019/roles/role/name/data">http://localhost:2019/roles/role/name/data</a>
     *
     * @param roleName The name of the role you seek
     * @return JSON object of the role you seek
     * @see RoleService#findByName(String) RoleService.findByName(String)
     */
    @RequestMapping(value = "/role/name/{roleName}", method = RequestMethod.GET, produces = "application/json")
    @ApiOperation(value = " return a role with the path parmeter of roleName")
    @ApiResponses(value = {
        @ApiResponse(code = 200,
            message = "Role Found",
            response = Role.class),
        @ApiResponse(code = 404,
            message = "Role NotFound",
            response = ResourceNotFoundException.class)})
    public ResponseEntity<?> getRoleByName(
        @PathVariable
            String roleName)
    {
        Role r = roleService.findByName(roleName);
        return new ResponseEntity<>(r,
            HttpStatus.OK);
    }

    /**
     * Given a complete Role object, create a new Role record
     * <br>Example: <a href="http://localhost:2019/roles/role">http://localhost:2019/roles/role</a>
     *
     * @param newRole A complete new Role object
     * @return A location header with the URI to the newly created role and a status of CREATED
     * @see RoleService#save(Role) RoleService.save(Role)
     */
    @RequestMapping(value = "/role", method = RequestMethod.POST, consumes = "application/json")
    @ApiOperation(value = "adds one role to the datbase from the request body Role newRole")
    @ApiResponses(value = {
        @ApiResponse(code = 201,
            message = "Role Created"),
        @ApiResponse(code = 400,
            message = "Bad Request",
            response = ErrorDetail.class)})
    public ResponseEntity<?> addNewRole(
        @Valid
        @RequestBody
            Role newRole)
    {
        // ids are not recognized by the Post method
        newRole.setRoleid(0);
        newRole = roleService.save(newRole);

        // set the location header for the newly created resource
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newRoleURI = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{roleid}")
            .buildAndExpand(newRole.getRoleid())
            .toUri();
        responseHeaders.setLocation(newRoleURI);

        return new ResponseEntity<>(null,
            responseHeaders,
            HttpStatus.CREATED);
    }

    /**
     * The process allows you to update a role name only!
     * <br>Example: <a href="http://localhost:2019/roles/role/3">http://localhost:2019/roles/role/3</a>
     *
     * @param roleid  The primary key (long) of the role you wish to update
     * @param newRole The new name (String) for the role
     * @return Status of OK
     */
    @RequestMapping(value = "/role/{roleid}", method = RequestMethod.PUT, consumes = "application/json")
    @ApiOperation(value = "updates the role at the path parameter rolid")
    @ApiResponses(value = {
        @ApiResponse(code = 200,
            message = "Role Found",
            response = Role.class),
        @ApiResponse(code = 201,
        message = "Role Created"),
        @ApiResponse(code = 404,
            message = "Role Not Found",
            response = ResourceNotFoundException.class)})
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> putUpdateRole(
        @PathVariable
            long roleid,
        @Valid
        @RequestBody
            Role newRole)
    {
        newRole = roleService.update(roleid,
            newRole);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
