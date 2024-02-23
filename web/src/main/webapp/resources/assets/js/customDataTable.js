var table;
var dataTableToCompleteList;

$(document).ready(function () {
    // Verificar si la tabla existe antes de destruirla
    if ($.fn.DataTable.isDataTable('#dataTableToCompleteList')) {
        dataTableToCompleteList = $('#dataTableToCompleteList').DataTable();
        dataTableToCompleteList.destroy();
    }

    // Construir las tablas después de la destrucción
    $('#dataTable, #dataTableToEdit, #dataTableOrderDesc').on('init.dt', function () {
        // Aquí puedes realizar cualquier acción que necesites después de la inicialización de DataTable
        console.log('Tabla inicializada: ' + $(this).attr('id'));
    });
    
    setTimeout(function () {
        
        table = $('#dataTable').DataTable( {
            'sScrollX': '100%',
            dom: "Bfrtip",
            destroy: true,
            searching: true,
            paging: false,
            bInfo: false,
            language:{
                "info": "Mostrando _START_ a _END_ de _TOTAL_",
                "infoEmpty":    "Mostrando 0 de 0",
                "lengthMenu":   "Mostrar _MENU_",
                "infoFiltered": "(filtrado de _MAX_ entradas totales)",
                "search":   "Buscar:",
                "emptyTable": "No hay datos cargados para esta tabla",
                "paginate": {
                    "first":      "First",
                    "last":       "Last",
                    "next":       "Siguiente",
                    "previous":   "Anterior"
                }
            },
            buttons: [
                {
                    extend: 'pdfHtml5',
                    text: '<i data-toggle="tooltip" title="PDF" class="fa fa-file-pdf-o" aria-hidden="true"></i>',
                    titleAttr: 'PDF',
                    orientation: 'landscape',
                    exportOptions: {
                        columns: ':visible'
                    },
                    className: 'btn btn-primary'
                },
                {
                    extend: 'excelHtml5',
                    text: '<i data-toggle="tooltip" title="PDF" class="fa fa-file-pdf-o" aria-hidden="true"></i>',
                    titleAttr: 'PDF',
                    exportOptions: {
                        columns: ':visible',
                        modifier: {
                            page: 'current'
                        }
                    },
                    className: 'btn btn-primary'
                },
                {
                    extend: 'colvis',
                    text: '<i class="fa fa-sliders" aria-hidden="true"></i>',
                    titleAttr: 'Filtro de Columnas',
                    columnText: function (dt, idx, title) {
                        return (idx + 1) + ': ' + title;
                    },
                    className: 'btn btn-primary'
                },
                {
                    extend: 'copyHtml5',
                    text: '<i class="fa fa-clone" aria-hidden="true"></i>',
                    titleAttr: 'Copiar en portapapeles',
                    copySuccess: {
                        1: "Copied one row to clipboard",
                        _: "Copied %d rows to clipboard"
                    },
                    exportOptions: {
                        columns: ':visible'
                    },
                    copyTitle: 'Copiar en portapapeles',
                    copyKeys: 'Press <i>ctrl</i> or <i>\u2318</i> + <i>C</i> to copy the table data<br>to your system clipboard.<br><br>To cancel, click this message or press escape.',
                    className: 'btn btn-primary'
                },
                {
                    extend: 'print',
                    text: '<i class="fa fa-print" aria-hidden="true"></i>',
                    titleAttr: 'Imprimir',
                    exportOptions: {
                        columns: ':visible',
                        modifier: {
                            page: 'current'
                        }
                    },
                    className: 'btn btn-primary'
                }
            ],
        } ).columns.adjust().draw();
    
        var tableToEdit = $('#dataTableToEdit').DataTable( {
            'sScrollX': '100%',
            dom: "Bfrtip",
            destroy: true,
            buttons: [
                {
                    extend: 'pdfHtml5',
                    text: '<i data-toggle="tooltip" title="PDF" class="fa fa-file-pdf-o" aria-hidden="true"></i>',
                    titleAttr: 'PDF',
                    exportOptions: {
                        columns: ':visible'
                    },
                    className: 'btn btn-primary'
                },
                {
                    extend: 'excelHtml5',
                    text: '<i data-toggle="tooltip" title="PDF" class="fa fa-file-pdf-o" aria-hidden="true"></i>',
                    titleAttr: 'PDF',
                    exportOptions: {
                        columns: ':visible',
                        modifier: {
                            page: 'current'
                        }
                    },
                    className: 'btn btn-primary'
                },
                {
                    extend: 'colvis',
                    text: '<i class="fa fa-sliders" aria-hidden="true"></i>',
                    titleAttr: 'Filtro de Columnas',
                    columnText: function (dt, idx, title) {
                        return (idx + 1) + ': ' + title;
                    },
                    className: 'btn btn-primary'
                },
                {
                    extend: 'copyHtml5',
                    text: '<i class="fa fa-clone" aria-hidden="true"></i>',
                    titleAttr: 'Copiar en portapapeles',
                    copySuccess: {
                        1: "Copied one row to clipboard",
                        _: "Copied %d rows to clipboard"
                    },
                    exportOptions: {
                        columns: ':visible'
                    },
                    copyTitle: 'Copiar en portapapeles',
                    copyKeys: 'Press <i>ctrl</i> or <i>\u2318</i> + <i>C</i> to copy the table data<br>to your system clipboard.<br><br>To cancel, click this message or press escape.',
                    className: 'btn btn-primary'
                },
                {
                    extend: 'print',
                    text: '<i class="fa fa-print" aria-hidden="true"></i>',
                    titleAttr: 'Imprimir',
                    exportOptions: {
                        columns: ':visible',
                        modifier: {
                            page: 'current'
                        }
                    },
                    className: 'btn btn-primary'
                }
            ],
        } ).columns.adjust().draw();
    
        dataTableToCompleteList = $('#dataTableToCompleteList').DataTable({
            stateSave: true,
            destroy: true,
            stateDuration: -1,
            'sScrollX': '100%',
            lengthChange: true,
            lengthMenu: [ [ 10, 50, 100, -1 ], [ '10', '50', '100', 'Todos' ]],
            dom: "Blfrtip",
            language:{
                "info": "Mostrando _START_ a _END_ de _TOTAL_",
                "infoEmpty":    "Mostrando 0 de 0",
                "lengthMenu":   "Mostrar _MENU_",
                "infoFiltered": "(filtrado de _MAX_ entradas totales)",
                "search":   "Buscar:",
                "emptyTable": "No hay datos cargados para esta tabla",
                "paginate": {
                    "first":      "First",
                    "last":       "Last",
                    "next":       "Siguiente",
                    "previous":   "Anterior"
                },
            },
            buttons: [
                {
                    extend: 'pdfHtml5',
                    text: '<i data-toggle="tooltip" title="PDF" class="fa fa-file-pdf-o" aria-hidden="true"></i>',
                    titleAttr: 'PDF',
                    orientation: 'landscape',
                    exportOptions: {
                        columns: ':visible'
                    },
                    className: 'dataTableButton',
                },
                {
                    extend: 'excelHtml5',
                    text: '<i class="fa fa-file-excel-o" aria-hidden="true"></i>',
                    titleAttr: 'Excel',
                    exportOptions: {
                        columns: ':visible',
                        modifier: {
                            page: 'current'
                        }
                    },
                    className: 'dataTableButton'
                },
                {
                    extend: 'colvis',
                    text: '<i class="fa fa-sliders" aria-hidden="true"></i>',
                    titleAttr: 'Filtro de Columnas',
                    columnText: function (dt, idx, title) {
                        return (idx + 1) + ': ' + title;
                    },
                    className: 'dataTableButton'
                },
                {
                    extend: 'copyHtml5',
                    text: '<i class="fa fa-clone" aria-hidden="true"></i>',
                    titleAttr: 'Copiar en portapapeles',
                    copySuccess: {
                        1: "Copied one row to clipboard",
                        _: "Copied %d rows to clipboard"
                    },
                    exportOptions: {
                        columns: ':visible'
                    },
                    copyTitle: 'Copiar en portapapeles',
                    copyKeys: 'Press <i>ctrl</i> or <i>\u2318</i> + <i>C</i> to copy the table data<br>to your system clipboard.<br><br>To cancel, click this message or press escape.',
                    className: 'dataTableButton'
                },
                {
                    extend: 'print',
                    text: '<i class="fa fa-print" aria-hidden="true"></i>',
                    titleAttr: 'Imprimir',
                    exportOptions: {
                        columns: ':visible',
                        modifier: {
                            page: 'current'
                        }
                    },
                    className: 'dataTableButton'
                }
            ],
        }).columns.adjust().draw();
    
        dataTableToCompleteList.buttons().container().appendTo( '#example_wrapper .col-md-6:eq(0)' );
    
    
        var dataTableOrderDesc = $('#dataTableOrderDesc').DataTable( {
            'sScrollX': '100%',
            dom: "Bfrtip",
            destroy: true,
            order: [[ 0, "desc" ]],
            searching: true,
            paging: false,
            bInfo: false,
            language:{
                "info": "Mostrando _START_ a _END_ de _TOTAL_",
                "infoEmpty":    "Mostrando 0 de 0",
                "lengthMenu":   "Mostrar _MENU_",
                "infoFiltered": "(filtrado de _MAX_ entradas totales)",
                "search":   "Buscar:",
                "paginate": {
                    "first":      "First",
                    "last":       "Last",
                    "next":       "Siguiente",
                    "previous":   "Anterior"
                },
            },
            buttons: [
                {
                    extend: 'pdfHtml5',
                    text: '<i data-toggle="tooltip" title="PDF" class="fa fa-file-pdf-o" aria-hidden="true"></i>',
                    titleAttr: 'PDF',
                    orientation: 'landscape',
                    exportOptions: {
                        columns: ':visible'
                    },
                    className: 'btn btn-primary'
                },
                {
                    extend: 'excelHtml5',
                    text: '<i data-toggle="tooltip" title="PDF" class="fa fa-file-pdf-o" aria-hidden="true"></i>',
                    titleAttr: 'PDF',
                    exportOptions: {
                        columns: ':visible',
                        modifier: {
                            page: 'current'
                        }
                    },
                    className: 'btn btn-primary'
                },
                {
                    extend: 'colvis',
                    text: '<i class="fa fa-sliders" aria-hidden="true"></i>',
                    titleAttr: 'Filtro de Columnas',
                    columnText: function (dt, idx, title) {
                        return (idx + 1) + ': ' + title;
                    },
                    className: 'btn btn-primary'
                },
                {
                    extend: 'copyHtml5',
                    text: '<i class="fa fa-clone" aria-hidden="true"></i>',
                    titleAttr: 'Copiar en portapapeles',
                    copySuccess: {
                        1: "Copied one row to clipboard",
                        _: "Copied %d rows to clipboard"
                    },
                    exportOptions: {
                        columns: ':visible'
                    },
                    copyTitle: 'Copiar en portapapeles',
                    copyKeys: 'Press <i>ctrl</i> or <i>\u2318</i> + <i>C</i> to copy the table data<br>to your system clipboard.<br><br>To cancel, click this message or press escape.',
                    className: 'btn btn-primary'
                },
                {
                    extend: 'print',
                    text: '<i class="fa fa-print" aria-hidden="true"></i>',
                    titleAttr: 'Imprimir',
                    exportOptions: {
                        columns: ':visible',
                        modifier: {
                            page: 'current'
                        }
                    },
                    className: 'btn btn-primary'
                }
            ],
        } ).columns.adjust().draw();
    
        $('a.toggle-vis').on( 'click', function (e) {
            e.preventDefault();
    
            // Get the column API object
            var column = table.column( $(this).attr('data-column') );
    
            // Toggle the visibility
            column.visible( ! column.visible() );
        } ).columns.adjust().draw();

    }, 700);

});