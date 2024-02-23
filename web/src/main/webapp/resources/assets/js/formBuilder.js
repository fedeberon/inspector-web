var formBuilder;

function showForm(id){
    var formData = document.getElementById("data-" + id).value;

    var fbRender = document.getElementById("rendered-form");
    $(fbRender).formRender({ formData: formData });

    $("#modal-show-form").modal('show');
}

function closeBuilder(){
    document.getElementById("rendered-form").innerHTML = "";
    goBackToEdit();
    $('#modal-create-form').modal('hide');

}

function renderForm() {
    if(formBuilder){
        var xmlForm = formBuilder.actions.getData('xml');

        var fbRender = document.getElementById('rendered-form');

        var formRenderOpts = {
            formData: xmlForm,
            dataType: 'xml'
        };

        $(fbRender).formRender(formRenderOpts);

        $('#fb-editor').addClass('d-none');
        $('#rendered-form').removeClass('d-none');
        $('#fb-buttons').removeClass('d-none');
    }else{
        console.log("render form not working");
    }
}

function goBackToEdit(){
    $('#fb-editor').removeClass('d-none');
    $('#rendered-form').addClass('d-none');
    $('#rendered-form').innerHTML = "";
    $('#fb-buttons').addClass('d-none');
}

function loadForm(){
    if(formBuilder) {
        var jsonForm = formBuilder.actions.getData('json'); //get json data of form

        document.getElementById("items").value = jsonForm; //set data into the field

        document.getElementById("rendered-form").innerHTML = "";
        goBackToEdit();
        $("#createFormItems").removeClass("btn-secondary");
        $("#createFormItems").addClass("btn-success");
        document.getElementById("createFormItems").innerHTML = "Ver formulario";
        $("#modal-create-form").modal('hide');
    }else{
        $.notify({
            title: '<strong>Hubo un problema!</strong>',
            message: 'Se produjo un error al crear los items del formulario.'
        }, {
            type: 'danger'
        });
    }
}

function initFormBuilder(){
    var options = {
        controlOrder: [
            'text',
            'textarea',
            'checkbox-group',
            'select'
        ],
        disabledActionButtons: ['data', 'save' , 'clear'],
        disableFields: ['autocomplete', 'hidden' , 'date' , 'button' , 'file' , 'header' , 'number' , 'starRating' , 'paragraph'],
        disabledAttrs: ['access', 'className', 'name', 'description', 'min', 'multiple', 'other', 'rows', 'step', 'style', 'subtype'],
        actionButtons: [{
            id: 'render',
            className: 'btn btn-fill btn-primary',
            label: 'Vista previa',
            type: 'button',
            events: {
                click: function (){
                    renderForm();
                }
            }
        }],
        i18n: {
            override: {
                'en-US': {
                    addOption: 'A&ntilde;adir Opci&oacute;n +',
                    allFieldsRemoved: 'Todos los campos fueron eliminados.',
                    allowMultipleFiles: 'Permitir seleccionar',
                    autocomplete: 'Autocompletar',
                    button: 'Botón',
                    cannotBeEmpty: 'Este campo no puede estar vacio',
                    checkboxGroup: 'Grupo de casillas',
                    checkbox: 'Casilla',
                    checkboxes: 'Casillas',
                    className: 'Clase',
                    clearAllMessage: '&iquest;Estas seguro de querer quitar todos los campos?',
                    clear: 'Eliminar',
                    close: 'Cerrar',
                    content: 'Contenido',
                    copy: 'Copiar al Portapapeles',
                    copyButton: '&#43;',
                    copyButtonTooltip: 'Copiar',
                    dateField: 'Campo de Fecha',
                    description: 'Texto-ayuda',
                    descriptionField: 'Descripci&oacute;n',
                    devMode: 'Modo Desarrollador',
                    editNames: 'Editar Nombres',
                    editorTitle: 'Elementos del formulario',
                    editXML: 'Editar XML',
                    enableOther: 'Permitir otros',
                    fieldDeleteWarning: 'falso',
                    fieldVars: 'Variables del campo',
                    enableOtherMsg: 'Permitir a los usuarios ingresar una opci&oacute;n no listada',
                    fieldNonEditable: 'Este campo no se puede editar.',
                    fieldRemoveWarning: '&iquest;Estas seguro de querer quitar este campo?',
                    fileUpload: 'Subida de archivo',
                    formUpdated: 'Formulario Actualizado',
                    getStarted: 'Arrastra un campo de la derecha a esta zona',
                    header: 'Título',
                    hide: 'Editar',
                    hidden: 'Campo oculto',
                    inline: 'Lineal',
                    inlineDesc: 'Mostrar lineal',
                    label: 'Etiqueta',
                    labelEmpty: 'Esta etiqueta no puede estar vacia',
                    limitRole: 'Limitar el acceso a una o m&aacute;s de las siguientes funciones:',
                    mandatory: 'Obligatorio',
                    maxlength: 'M&aacute;x. longitud',
                    minOptionMessage: 'Este campo necesita al menos 2 opciones',
                    multipleFiles: 'Varios archivos',
                    name: 'Nombre',
                    no: 'No',
                    noFieldsToClear: 'No hay campos para borrar',
                    number: 'Número',
                    off: 'Desactivado',
                    on: 'Activo',
                    option: 'Opción',
                    options: 'Opciónes',
                    optional: 'opcional',
                    optionLabelPlaceholder: 'Etiqueta',
                    optionValuePlaceholder: 'Valor',
                    optionEmpty: 'La opción necesita un valor',
                    other: 'Otro',
                    paragraph: 'Párrafo',
                    placeholder: 'Placeholder',
                    'placeholder.value': 'Valor',
                    'placeholder.label': 'Etiqueta',
                    'placeholder.text': 'Introduce algún texto',
                    'placeholder.textarea': 'Introduce un montón de texto',
                    'placeholder.email': 'Introduce un email',
                    'placeholder.placeholder': 'Placeholder',
                    'placeholder.className': 'clases separadas por espacios',
                    'placeholder.password': 'Introduce una contrase&ntilde;a',
                    preview: 'Vista Previa',
                    radioGroup: 'Grupo de Opciones',
                    radio: 'Opcion Radio',
                    removeMessage: 'Quitar elemento',
                    removeOption: 'Quitar opcion',
                    remove: '&#215;',
                    required: 'Obligatorio',
                    richText: 'Editor WYSIWYG',
                    roles: 'Acceso',
                    rows: 'Filas',
                    save: 'Guardar',
                    selectOptions: 'Opciones',
                    select: 'Seleccionable',
                    selectColor: 'Selecciona un color',
                    selectionsMessage: 'Permitir m&uacute;ltiples selecciones',
                    size: 'Tama&ntilde;o',
                    'size.xs': 'Extra peque&ntilde;o',
                    'size.sm': 'Peque&ntilde;o',
                    'size.m': 'Por defecto',
                    'size.lg': 'Grande',
                    style: 'Estilo',
                    styles: {
                        btn: {
                            'default': 'Default',
                            danger: 'Danger',
                            info: 'Info',
                            primary: 'Primary',
                            success: 'Success',
                            warning: 'Warning'
                        }
                    },
                    subtype: 'Tipo',
                    text: 'Campo de Texto',
                    textArea: '&Aacute;rea de texto',
                    toggle: 'Interruptor',
                    warning: 'Advertencia!',
                    value: 'Valor',
                    viewJSON: '{  }',
                    viewXML: '&lt;/&gt;',
                    yes: 'Si'
                }

            }
        }
    };

    if(document.getElementById("items").value && document.getElementById("items").value !== ''){
        options.defaultFields = JSON.parse(document.getElementById("items").value);
    }

    formBuilder = $(document.getElementById('fb-editor')).formBuilder(options);
}