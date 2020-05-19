/*
function modalId(id, tagId) {
    let a = document.getElementById(tagId);
    a.setAttribute("value", id);
}

$('#type-btn').click(function () {
    alert('lol');
});

*/
function listUser() {
    $.getJSON('http://localhost:8081/api/list', function (json) {
        let tr = [];
        for (let i in json) {
            tr.push('<tr>');
            tr.push('<td>' + json[i].id + '</td>');
            tr.push('<td>' + json[i].firstName + '</td>');
            tr.push('<td>' + json[i].lastName + '</td>');
            tr.push('<td>' + json[i].age + '</td>');
            tr.push('<td>' + json[i].email + '</td>');
            tr.push('<td>' + json[i].allRoles + '</td>');
            tr.push('<td><button type="submit" class="btn btn-info info-edit" data-toggle="modal"\n' +
                'data-target="#editModal" data-whatever="@mdo" >Edit</button></td>');
            tr.push('<td><button type="submit" class="btn btn-danger danger-delete" data-toggle="modal"' +
                'data-target="#deleteModal" data-whatever="@mdo" >Delete</button></td>');
            tr.push('</tr>');
        }
        $('.tbody-admin').append($(tr.join('')));
    });
}

listUser();

$('#nav-admin-tab').click(function () {
    $("tbody").empty();
    listUser();
})

$(function () {
    $.getJSON('http://localhost:8081/api/admin', function (json) {
        let tr = [];
        console.log(json)
        tr.push('<tr>');
        tr.push('<td>' + json.id + '</td>');
        tr.push('<td>' + json.firstName + '</td>');
        tr.push('<td>' + json.lastName + '</td>');
        tr.push('<td>' + json.age + '</td>');
        tr.push('<td>' + json.email + '</td>');
        tr.push('<td>' + json.allRoles + '</td>');
        tr.push('</tr>');

        $('#tbody-single').append($(tr.join('')));
    });

    $('#btn-delete-user').click(function () {
        let id = $('#deleteId').val();

        $.ajax({
            type: 'DELETE',
            url: 'http://localhost:8081/api/delete/' + id,
            cache: false
        });

        $("tbody").empty();
        listUser();

        $('#deleteModal').modal('hide');
        return false;
    })

    $('#btn-edit-user').click(function () {
        let role = $('#eRole').val();

        let jsonObj = {
            id: $('#editId').val(),
            firstName: $('#eFirstName').val(),
            lastName: $('#eLastName').val(),
            age: $('#eAge').val(),
            email: $('#eEmail').val(),
            password: $('#ePassword').val(),
        }

        console.log(jsonObj);

        $.ajax({
            type: 'POST',
            contentType: 'application/json; charset=utf-8',
            url: 'http://localhost:8081/api/update/' + role,
            data: JSON.stringify(jsonObj),
            cache: false,
            success: function (result) {
                $("tbody").empty();
                listUser();
            },
            error: function (err) {
                $("#h1-admin").append("<h6><div class=\"alert alert-danger alert-dismissible fade show\" role=\"alert\">\n" +
                    "  <strong>Warning!</strong> Wrong data!\n" +
                    "  <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-label=\"Close\">\n" +
                    "    <span aria-hidden=\"true\">&times;</span>\n" +
                    "  </button>\n" +
                    "</div></h6>")
            }
        });

        $('#editModal').modal('hide');



        return false;
    });

    $('#btn-new-user').click(function () {
        let role = $('#aRole').val();

        let jsonObj = {
            firstName: $('#aFirstName').val(),
            lastName: $('#aLastName').val(),
            age: $('#aAge').val(),
            email: $('#aEmail').val(),
            password: $('#aPassword').val(),
        }

        console.log(jsonObj);

        $.ajax({
            type: 'POST',
            contentType: 'application/json; charset=utf-8',
            url: 'http://localhost:8081/api/add/' + role,
            data: JSON.stringify(jsonObj),
            cache: false,
            success: function (result) {
                $("#h1-admin").append("<h6><div class=\"alert alert-success alert-dismissible fade show\" role=\"alert\">\n" +
                    "  <strong>Success!</strong> New user was added.\n" +
                    "  <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-label=\"Close\">\n" +
                    "    <span aria-hidden=\"true\">&times;</span>\n" +
                    "  </button>\n" +
                    "</div></h6>")
            },
            error: function (jqXHR, err) {
                if(jqXHR.status === 406) {
                    $("#h1-admin").append("<h6><div class=\"alert alert-danger alert-dismissible fade show\" role=\"alert\">\n" +
                        "  <strong>Warning!</strong> This user already exist.\n" +
                        "  <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-label=\"Close\">\n" +
                        "    <span aria-hidden=\"true\">&times;</span>\n" +
                        "  </button>\n" +
                        "</div></h6>")
                } else if(jqXHR.status === 400) {
                    $("#h1-admin").append("<h6><div class=\"alert alert-danger alert-dismissible fade show\" role=\"alert\">\n" +
                        "  <strong>Warning!</strong> Enter all fields!\n" +
                        "  <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-label=\"Close\">\n" +
                        "    <span aria-hidden=\"true\">&times;</span>\n" +
                        "  </button>\n" +
                        "</div></h6>")
                }
            }
        });
    });

    $('tbody').on('click', 'button.danger-delete', function () {
        let data = $(this).closest("tr").children("td").map(function () {
            return $(this).text();
        });

        $('#deleteId').val(data[0]);
        $('#dFirstName').val(data[1]);
        $('#dLastName').val(data[2]);
        $('#dAge').val(data[3]);
        $('#dEmail').val(data[4]);
    });

    $('tbody').on('click', 'button.info-edit', function () {
        let data = $(this).closest("tr").children("td").map(function () {
            return $(this).text();
        });

        $('#editId').val(data[0]);
        $('#eFirstName').attr('placeholder', data[1]);
        $('#eLastName').attr('placeholder', data[2]);
        $('#eAge').val(data[3]);
        $('#eEmail').attr('placeholder', data[4]);
    });
});















