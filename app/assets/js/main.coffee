$ -> 
    $r = $('#results')
    for x in [1..25] 
        do (x) ->
            time = 1500 + Math.ceil 5000 * Math.random()
            $el = $("<li>Doing run #{x},  wait=#{time}ms: <span class=running>RUNNING</span></li>").appendTo($r)
            $span = $('span', $el)
            p = $.get "/timeout/#{time}"
            p.done (response) -> 
               $span.removeClass('running')
                    .addClass('done')
                    .text("took #{response}")
