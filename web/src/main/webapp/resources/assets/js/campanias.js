$(document).ready(function() {
    $('#exampleModal').on('show.bs.modal', function (event) {
        let button = $(event.relatedTarget) // Button that triggered the modal
        let reservaId = button.data('reserva-id')
        let campaniaName = button.data('campania-name')
        let orderNumber =  $("#order-number-div-" + reservaId).attr("data-order-number");

        let modal = $(this)
        modal.find('.modal-title').text('Editar nro de orden de ' + campaniaName)

        modal.find('.modal-body input#order-number').val(orderNumber);
        modal.find('.modal-body input#reserva-id').val(reservaId);
    })
});

function updateOrderNumber() {
    $(".load").show();
    let orderNumber = $('#order-number').val();
    let reservaId = $('#reserva-id').val();
    let data = {
        "ordenNumber": orderNumber,
        "id": reservaId
    };
    $.ajax({
        url: '../api/relevamiento/reservaciones/updateOrderNumber',
        contentType: 'application/json',
        dataType: "json",
        method: 'PUT',
        data: JSON.stringify(data),
        success: function(response) {
            console.info('El número de orden se actualizó correctamente.');
            $("#order-number-" + reservaId).text(response.ordenNumber);
            $("#order-number-div-" + reservaId).attr("data-order-number", orderNumber);
            $('#exampleModal').modal('hide');
            $(".load").hide();
        },
        error: function(error) {
            console.error('Error al actualizar el número de orden:', error);
            $(".load").hide();
        }
    });
}