// Функция для отображения формы создания пользователя
function showCreateUserForm() {
  $('#usersTable').hide();
  $('#createUserFormContainer').show();
}

// Функция для отмены создания пользователя
function cancelCreateUser() {
  $('#usersTable').show();
  $('#createUserFormContainer').hide();
}

// Функция для получения списка всех пользователей
function getAllUsers() {
  $.ajax({
    url: '/api/admin',
    method: 'GET',
    success: function(users) {
      var tableBody = $('#showUsers');
      tableBody.empty();

      $.each(users, function(index, user) {
        var row = $('<tr>');
        row.append($('<td>').text(user.id));
        row.append($('<td>').text(user.name));
        row.append($('<td>').text(user.lastName));
        row.append($('<td>').text(user.username));
        row.append($('<td>').text(user.roles[0].name));

        var editButton = $('<button>').text('Edit').addClass('btn btn-primary btn').attr('data-bs-toggle', 'modal').attr('data-bs-target', '#modalEdit').attr('data-user-id', user.id);
        var deleteButton = $('<button>').text('Delete').addClass('btn btn-danger btn').attr('data-bs-toggle', 'modal').attr('data-bs-target', '#modalDelete').attr('data-user-id', user.id);

        var editCell = $('<td>').append(editButton);
        var deleteCell = $('<td>').append(deleteButton);

        row.append(editCell);
        row.append(deleteCell);

        tableBody.append(row);
      });
    },
    error: function() {
      alert('Ошибка при получении списка пользователей');
    }
  });
}

$(document).ready(function() {
  // Получение данных о текущем пользователе
  $.ajax({
    url: '/api/admin/current',
    method: 'GET',
    success: function(user) {
      $('#username').text(user.username);
      $('#userRole').text(user.roles[0].name);
    }
  });

  // Получение списка всех пользователей
  getAllUsers();

  // Обработчик кнопки редактирования пользователя
  $('#modalEditButton').click(function() {
    var userId = $('#editId').val();
    var user = {
      id: userId,
      name: $('#editName').val(),
      lastName: $('#editLastName').val(),
      username: $('#editUsername').val(),
      password: $('#editPassword').val(),
      roles: [{ name: $('#editRole').val() }] // Предполагается, что у пользователя может быть только одна роль
    };

    $.ajax({
      url: '/api/admin/' + userId,
      method: 'PUT',
      contentType: 'application/json',
      data: JSON.stringify(user),
      success: function() {
        // Обновление данных на странице
        $('#modalEdit').modal('hide');
        updateUserRow(userId, user);
      }
    });
  });

  // Обработчик кнопки удаления пользователя
  $('#modalDeleteButton').click(function() {
    var userId = $('#deleteId').val();
    $.ajax({
      url: '/api/admin/' + userId,
      method: 'DELETE',
      success: function() {
        // Обновление данных на странице
        $('#modalDelete').modal('hide');
        removeUserRow(userId);
      }
    });
  });

  // Заполнение модального окна редактирования данными пользователя при открытии
  $('#modalEdit').on('show.bs.modal', function(event) {
    var button = $(event.relatedTarget);
    var userId = button.data('user-id');
    var modal = $(this);

    // Получение данных пользователя по идентификатору
    $.ajax({
      url: '/api/admin/' + userId,
      method: 'GET',
      success: function(user) {
        modal.find('#editId').val(user.id);
        modal.find('#editName').val(user.name);
        modal.find('#editLastName').val(user.lastName);
        modal.find('#editUsername').val(user.username);
        modal.find('#editPassword').val(user.password);
        modal.find('#editRole').val(user.roles[0].name);
      }
    });
  });

  // Заполнение модального окна удаления пользователя при открытии
  $('#modalDelete').on('show.bs.modal', function(event) {
    var button = $(event.relatedTarget);
    var userId = button.data('user-id');
    var modal = $(this);

    // Получение данных пользователя по идентификатору
    $.ajax({
      url: '/api/admin/' + userId,
      method: 'GET',
      success: function(user) {
        modal.find('#deleteId').val(user.id);
        modal.find('#deleteName').val(user.name);
        modal.find('#deleteLastName').val(user.lastName);
        modal.find('#deleteUsername').val(user.username);
        modal.find('#deletePassword').val(user.password);
        modal.find('#deleteRole').val(user.roles[0].name);
      }
    });
  });

  // Обновление данных пользователя в таблице
  function updateUserRow(userId, updatedUser) {
    var row = $('button[data-user-id="' + userId + '"]').closest('tr');
    row.find('td:nth-child(2)').text(updatedUser.name);
    row.find('td:nth-child(3)').text(updatedUser.lastName);
    row.find('td:nth-child(4)').text(updatedUser.username);
    row.find('td:nth-child(5)').text(updatedUser.roles[0].name);
 $('#username').text(updatedUser.username);
 $('#userRole').text(updatedUser.roles[0].name);
  }

  // Удаление данных пользователя из таблицы
  function removeUserRow(userId) {
    var row = $('button[data-user-id="' + userId + '"]').closest('tr');
    row.remove();
   $('#username').text(user.username);
   $('#userRole').text(user.roles[0].name);
  }

  // Обработчик кнопки создания пользователя
  $('#createUserForm').submit(function(event) {
    event.preventDefault();
    createUser();
  });

  // Обработчик кнопки отмены создания пользователя
  $('#cancelCreateUserButton').click(function() {
    cancelCreateUser();
  });
});

// Функция для создания пользователя
function createUser() {
  var user = {
    name: $('#createName').val(),
    lastName: $('#createLastName').val(),
    username: $('#createUsername').val(),
    password: $('#createPassword').val(),
     roles: []
  };
var role = $('#createRole').val(); // Получить значение поля ввода роли

  if (role === 'ADMIN') {
    user.roles.push({ id: 1, name: 'ADMIN' }); // Добавить роль "ADMIN"
  } else if (role === 'USER') {
    user.roles.push({ id: 2, name: 'USER' }); // Добавить роль "USER"
  }
  $.ajax({
    url: '/api/admin',
    method: 'POST',
    contentType: 'application/json',
    data: JSON.stringify(user),
    success: function() {
      // Очистка полей формы
      $('#createName').val('');
      $('#createLastName').val('');
      $('#createUsername').val('');
      $('#createPassword').val('');

      // Обновление списка пользователей
      getAllUsers();

      // Переключение на таблицу пользователей и скрытие формы
      $('#usersTable').show();
      $('#createUserFormContainer').hide();
    },

  });
}
